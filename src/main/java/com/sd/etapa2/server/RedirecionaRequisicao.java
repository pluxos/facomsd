package com.sd.etapa2.server;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import com.sd.etapa2.util.Operacao;

//Redirecionar a requisicao para o proximo server conferindo a finger table
//usa uma tabela com portas e ID dos servers
//Não completa
public class RedirecionaRequisicao implements Runnable {
	private HashMap<Integer, Integer> tabelaPortas;
	private ArrayBlockingQueue<Operacao> filaRedirecionamento;
	private Integer[] fingerTable;

	public RedirecionaRequisicao(HashMap<Integer, Integer> tabelaPortas,
			ArrayBlockingQueue<Operacao> filaRedirecionamento,Integer[] fingerTable) {
		this.tabelaPortas = tabelaPortas;
		this.filaRedirecionamento = filaRedirecionamento;
		this.fingerTable = fingerTable;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Operacao op = filaRedirecionamento.take();
				System.out.println("Redirecionando " + op.getTipo() + " Chave " + op.getChave() + " " + op.getValor());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
