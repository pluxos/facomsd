package com.sd.projeto1.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.sd.projeto1.model.Mapa;

public class Server {

	public static void main(String[] args) throws Exception {
		List<Mapa> logs = new ArrayList<Mapa>();

		loadData();
		ServerThreadDisk.imprimeMapa();

		System.out.println("Log do Disco Recuperado");
		System.out.println("Tamanho da Fila: " + ServerThreadDisk.mapa.size() + "\n");

		System.out.println("Servidor Iniciado...");
		new Thread(new ServerThreadReceive()).start();
	}

	private static void loadData() {
		Mapa mapa = new Mapa();
		StringBuilder sb = new StringBuilder();

		try (BufferedReader br = Files.newBufferedReader(Paths.get("app.log"))) {

			String line;
			while ((line = br.readLine()) != null) {
				String[] content = line.split("#");
				if(content.length>0){
					mapa.setTipoOperacaoId(Integer.parseInt(content[0]));
					mapa.setChave(Integer.parseInt(content[1]));
					if(content.length>2)
						mapa.setTexto(content[2]);

					if(mapa.getTipoOperacaoId() == 1 || mapa.getTipoOperacaoId() == 2)
						ServerThreadDisk.mapa.put(new BigInteger(String.valueOf(mapa.getChave())), mapa.getTexto());
					else if(mapa.getTipoOperacaoId() == 3)
						ServerThreadDisk.mapa.remove(new BigInteger(String.valueOf(mapa.getChave())));
				}

				sb.append(line).append("\n");
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}

		System.out.println(sb);

	}
}
