package com.sd.etapa2.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.sd.etapa2.util.Banco;
import com.sd.etapa2.util.Operacao;

//Retirar da filaLogs e criar os arquivos de logs e snapshot
public class InsereLog implements Runnable {

	private String arquivo;
	private ArrayBlockingQueue<Operacao> filaLogs;
	private Banco banco;
	private int cont;
	private int numLog;

	public InsereLog(String arquivo, ArrayBlockingQueue<Operacao> filaLogs, Banco banco) {
		this.arquivo = arquivo;
		this.filaLogs = filaLogs;
		this.banco = banco;

	}

	@Override
	public void run() {
		while(true) {
			criaLog();
			snapShot();
			for(int i = 0; i < 4; i++) {
				File file = new File(i+"log"+arquivo);
				file.delete();
			}
		}
		
	}

	//Criar os logs
	private void criaLog() {
		cont = 0;
		numLog = 1;
		while (true) {
			if(cont > 3) {
				cont = 0;
				numLog++;
			}
			if(numLog > 3) {
				break;
			}
			try {
				String valor = new String();
				Operacao operacao = filaLogs.take(); // Metodo take é bloqueante
				String log = numLog+"log"+arquivo;
				System.out.println("Inserindo " + operacao.getValor() + " em " + log);

				switch (operacao.getTipo()) {
				case CREATE:
					valor = operacao.getTipo() + "," + operacao.getChave() + "," + operacao.getValor();
					break;

				case DELETE:
					valor = operacao.getTipo() + "," + operacao.getChave();
					break;

				case UPDATE:
					valor = operacao.getTipo() + "," + operacao.getChave() + "," + operacao.getValor();
					break;
				default:
					break;
				}

				File fileLog = new File(log);
				if (!fileLog.exists()) {
					fileLog.createNewFile();
				}
				FileWriter fw = new FileWriter(log, true);
				PrintWriter printWriter = new PrintWriter(fw);
				printWriter.println(valor);
				printWriter.close();
				fw.close();
				cont++;
			} catch (InterruptedException | IOException e) {
				System.out.println("Erro na escrita do arquvivo.");
				return;
			}
		}
	}
	//Criar o snapshot
	private void snapShot() {
		try {
			String snapshot = "Snapshot" + arquivo;

			List<String> elementos = banco.getElementos();

			File snapshotFile = new File(snapshot);
			if (!snapshotFile.exists()) {
				snapshotFile.createNewFile();
			}

			FileWriter fw = new FileWriter(snapshot, false);
			PrintWriter printWriter = new PrintWriter(fw);

			for (String elemento : elementos) {
				printWriter.println(elemento);	
			}
			printWriter.close();
			fw.close();

		} catch (IOException e) {
			System.out.println("Erro na escrita do arquvivo.");
			return;
		}

	}
}
