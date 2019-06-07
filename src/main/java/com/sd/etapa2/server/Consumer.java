package com.sd.etapa2.server;

import java.util.concurrent.ArrayBlockingQueue;

import com.sd.etapa2.util.Operacao;

//Runnable para inserir nas filas
public class Consumer implements Runnable {

	private ArrayBlockingQueue<Operacao> filaComandos;
	private ArrayBlockingQueue<Operacao> filaBanco;
	private ArrayBlockingQueue<Operacao> filaLogs;

	public Consumer(ArrayBlockingQueue<Operacao> filaComandos, ArrayBlockingQueue<Operacao> filaBanco,
			ArrayBlockingQueue<Operacao> filaLogs) {
		this.filaComandos = filaComandos;
		this.filaBanco = filaBanco;
		this.filaLogs = filaLogs;
	}

	@Override
	public void run() {
		try {
			Operacao operacao = null;
			while (true) {
				operacao = filaComandos.take();
				filaBanco.put(operacao);
				filaLogs.put(operacao);
			}
		} catch (InterruptedException e) {
			return;
		}

	}
}
