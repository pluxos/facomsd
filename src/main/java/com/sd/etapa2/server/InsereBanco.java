package com.sd.etapa2.server;

import java.util.concurrent.ArrayBlockingQueue;

import com.sd.etapa2.util.Banco;
import com.sd.etapa2.util.Operacao;

//Retirar da filaBanco e inserir no banco
public class InsereBanco implements Runnable {

	private Banco banco;
	private ArrayBlockingQueue<Operacao> filaBanco;

	public InsereBanco(Banco banco, ArrayBlockingQueue<Operacao> filaBanco) {
		this.banco = banco;
		this.filaBanco = filaBanco;
	}

	@Override
	public void run() {

		try {
			Operacao operacao = null;
			while (true) {

				operacao = filaBanco.take(); // Operacao take é bloqueante

				System.out.println("Inserindo "+operacao.getValor()+" no banco");
				switch (operacao.getTipo()) {
				case CREATE:
					banco.setValor(operacao.getChave(), operacao.getValor());
					break;

				case DELETE:
					banco.deletaValor(operacao.getChave());
					break;

				case UPDATE:
					banco.atualizaValor(operacao.getChave(), operacao.getValor());;
					break;
				default:
					break;
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Erro na insercao no banco.");
			return;
		}

	}

}
