package com.sd.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.BlockingQueue;

public class Logger implements Runnable {

	protected BlockingQueue<ItemFila> f2;
	protected BlockingQueue<ItemFila> f3;
	Path path = Paths.get("./log");
	int escolha = 0;

	Logger() {
		this.f2 = F2.getInstance();
	}

	public void run() {

		try {
	           
            if (Files.exists(path)) {
				readAll();
				
			}
			String msg;
			DataOutputStream out;
			while (true) {
				ItemFila item = f2.take();
				writeCommand(item.toString());
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	String[] palavras(String x) {
		String str[] = new String[3];
		int tamanho = x.length();
		int espaco1 = x.indexOf(" ");
		str[0] = x.substring(0, espaco1);

		int espaco2 = x.indexOf(" ", (espaco1 + 1));
		str[1] = x.substring((espaco1 + 1), ((espaco2 == -1) ? tamanho : espaco2));
		if (espaco2 != -1) {
			str[2] = x.substring((espaco2 + 1), tamanho);
		}
		return str;

	}

	public void readAll() throws InterruptedException {
		try {
			Files.exists(path);

			for (String x : Files.readAllLines(path)) {

				String operacao = null;
				BigInteger chave = null;
				String valor = null;

				String[] lista = palavras(x);
				for (int i = 0; i < lista.length; i++) {
					operacao = lista[0];
					chave = new BigInteger(lista[1]);
					escolha = 1;
					if (lista[2] != null) {
						valor = lista[2];
						escolha = 2;
					}
				}
				this.f3 = F3.getInstance();
				ItemFila reg = (escolha == 2) ? new ItemFila(null, operacao, chave, valor)
						: new ItemFila(null, operacao, chave);
				f3.put(reg);
			}

		} catch (IOException e) {
			System.out.println("IO Error");
		}
	}

	private void writeCommand(String comando) {
		if (comando.substring(0, 4).equals("READ"))
			return;
		try {
			if (!Files.exists(path)) {
				System.out.println("* Arquivo não existe, criando arquivo *");
				Files.createFile(path);
			}
			comando = comando + "\n";
			Files.write(Paths.get("log"), comando.getBytes(), StandardOpenOption.APPEND);

		} catch (IOException e) {
			System.out.println("IO Error");
		}
	}
}