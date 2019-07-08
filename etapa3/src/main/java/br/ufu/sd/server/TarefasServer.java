package br.ufu.sd.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import br.ufu.sd.util.Comando;
import br.ufu.sd.util.Operacao;
import br.ufu.sd.util.OperacaoOutput;
import br.ufu.sd.util.OperacaoReply;
import br.ufu.sd.util.StatusResponse;

public class TarefasServer implements Runnable {

	private Socket socket;
	private ArrayBlockingQueue<OperacaoOutput> filaComandos;

	public TarefasServer(Socket socket, ArrayBlockingQueue<OperacaoOutput> filaComandos) {
		this.socket = socket;
		this.filaComandos = filaComandos;
	}

	@Override
	public void run() {

		try {
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

			//Opercoes recebidas pelo cliente
			Operacao operacao;
			//Opercoes vao ser encapsulada junto com Output para responder o client, antes de serem enfileiradas
			OperacaoOutput operacaoOutput;
			//Operacao resposta
			OperacaoReply operacaoReply;

			while (true) {

				operacao = (Operacao) input.readObject();

				if (operacao.getComando() == Comando.SAIR) {
					
					System.out.println("Finalizando conexao com cliente.");
					operacaoReply = new OperacaoReply(Comando.SAIR, StatusResponse.OK);
					output.writeObject(operacaoReply);
					output.flush();
					return;
					
				}
				
				operacaoOutput = new OperacaoOutput(operacao, output);

				filaComandos.put(operacaoOutput);

			}
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			System.out.println("Erro - Finalizando conexao com cliente");
			
		}

	}
}
