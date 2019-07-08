package br.ufu.sd.server;

import java.util.concurrent.ArrayBlockingQueue;

import br.ufu.sd.util.OperacaoOutput;

public class Consumer implements Runnable {

	private ArrayBlockingQueue<OperacaoOutput> filaComandos;
	private ArrayBlockingQueue<OperacaoOutput> filaBanco;

	public Consumer(ArrayBlockingQueue<OperacaoOutput> filaComandos, ArrayBlockingQueue<OperacaoOutput> filaBanco) {
		this.filaComandos = filaComandos;
		this.filaBanco = filaBanco;
	}

	@Override
	public void run() {
		try {
			OperacaoOutput operacao;
			while (true) {
				operacao = filaComandos.take();
				filaBanco.put(operacao);

			}
		} catch (InterruptedException e) {
			System.out.println("Erro " +e.getMessage() );
		}

	}
}
