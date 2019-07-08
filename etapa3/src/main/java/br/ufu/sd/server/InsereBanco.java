package br.ufu.sd.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import br.ufu.sd.util.Comando;
import br.ufu.sd.util.Operacao;
import br.ufu.sd.util.OperacaoOutput;
import br.ufu.sd.util.OperacaoReply;
import br.ufu.sd.util.StatusResponse;

public class InsereBanco implements Runnable {

	private Map<BigInteger, byte[]> banco;
	private ArrayBlockingQueue<OperacaoOutput> filaBanco;

	public InsereBanco(ArrayBlockingQueue<OperacaoOutput> filaBanco, Map<BigInteger, byte[]> banco) {
		this.banco = banco;
		this.filaBanco = filaBanco;
	}

	@Override
	public void run() {

		OperacaoOutput operacaoOut;
		Operacao operacao;
		OperacaoReply operacaoReply;
		StatusResponse status;
		ObjectOutputStream output;
		while (true) {

			try {
				operacaoOut = filaBanco.take();
				operacao = operacaoOut.getOperacao();
				output = operacaoOut.getOutput();

				switch (operacao.getComando()) {
				case CREATE:

					if (banco.containsKey(operacao.getChave())) {
						// ja exite a chave no banco
						status = StatusResponse.NOK;

					} else if (operacao.getChave() == null || operacao.getValor() == null) {
						// operacao ou chave null
						status = StatusResponse.NOK;

					} else {

						status = StatusResponse.OK;
						banco.put(operacao.getChave(), operacao.getValor());
					}

					operacaoReply = new OperacaoReply(Comando.CREATE, status);
					output.writeObject(operacaoReply);

					output.flush();
					break;

				case READ:

					if (banco.containsKey(operacao.getChave())) {
						status = StatusResponse.OK;
						operacaoReply = new OperacaoReply(Comando.READ, status);
						operacaoReply.setValor(banco.get(operacao.getChave()));

					} else {
						// Nao exite a chave no banco
						status = StatusResponse.NOK;
						operacaoReply = new OperacaoReply(Comando.READ, status);
					}

					output.writeObject(operacaoReply);
					output.flush();
					break;

				case UPDATE:

					if (!banco.containsKey(operacao.getChave())) {
						// Nao exite chave no banco
						status = StatusResponse.NOK;

					} else if (operacao.getChave() == null || operacao.getValor() == null) {
						// chave ou valor null
						status = StatusResponse.NOK;

					} else {
						status = StatusResponse.OK;
						banco.replace(operacao.getChave(), operacao.getValor());
					}

					operacaoReply = new OperacaoReply(Comando.UPDATE, status);
					output.writeObject(operacaoReply);

					output.flush();
					break;

				case DELETE:

					if (banco.containsKey(operacao.getChave())) {

						status = StatusResponse.OK;
						banco.remove(operacao.getChave());

					} else {
						// chave ou valor null
						status = StatusResponse.NOK;
					}

					operacaoReply = new OperacaoReply(Comando.DELETE, status);
					output.writeObject(operacaoReply);

					output.flush();
					break;
				default:
					break;

				}
			} catch (InterruptedException | IOException e) {
				System.out.println("Erro "+e.getMessage());
				break;
			}
		}

	}

}
