package br.ufu.sd.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.ufu.sd.server.Server;

public class StartServer {

	public static void main(String[] args) {
		
		try {
			FileReader arq;
			int id = Integer.parseInt(args[0]);
			String memberHost = null;
			int memberPort = 0;
			int socketPort = 0;

			// Arquivo de configuracao da cluster
			arq = new FileReader("cluster-config.txt");
			BufferedReader lerArq = new BufferedReader(arq);

			String s = lerArq.readLine();
			String[] linha = s.split(" ");

			String memberHost1 = ((linha[1]).split("="))[1];
			int memberPort1 = Integer.parseInt(((linha[2]).split("="))[1]);
			if (id == Integer.parseInt(((linha[0]).split("="))[1])) {
				memberHost = memberHost1;
				memberPort = memberPort1;
				socketPort = Integer.parseInt(((linha[3]).split("="))[1]);

			}

			s = lerArq.readLine();
			linha = s.split(" ");

			String memberHost2 = ((linha[1]).split("="))[1];
			int memberPort2 = Integer.parseInt(((linha[2]).split("="))[1]);
			if (id == Integer.parseInt(((linha[0]).split("="))[1])) {
				memberHost = memberHost2;
				memberPort = memberPort2;
				socketPort = Integer.parseInt(((linha[3]).split("="))[1]);

			}

			s = lerArq.readLine();
			linha = s.split(" ");

			String memberHost3 = ((linha[1]).split("="))[1];
			int memberPort3 = Integer.parseInt(((linha[2]).split("="))[1]);
			if (id == Integer.parseInt(((linha[0]).split("="))[1])) {
				memberHost = memberHost3;
				memberPort = memberPort3;
				socketPort = Integer.parseInt(((linha[3]).split("="))[1]);

			}

			lerArq.close();
			arq.close();
			System.out.println("Iniciando Server " + id +"...");

			Server server = new Server(id, memberHost, memberPort, memberHost1, memberPort1, memberHost2, memberPort2,
					memberHost3, memberPort3, socketPort);
			server.startServer();

		} catch (IOException e) {
			System.out.println("Erro " + e.getMessage());
		}
		System.exit(0);
	}

}
