package client;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApplication {
	public static void main(String[] args) throws UnknownHostException,IOException {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		// Start Socket
		
		//
		ClientCommands clientCommands = new ClientCommands("Matheus");
		executor.execute(clientCommands);

		ServerResponse serverResponse = new ServerResponse();
		executor.execute(serverResponse);
		
		/*Socket client = new Socket("127.0.0.1", 12345);
		System.out.println("Cliente conectado ao servidor");
		PrintStream saida = new PrintStream(client.getOutputStream());
		
		Scanner scan = new Scanner(System.in);
		
		while(scan.hasNextLine()) {
			saida.println(scan.nextLine());
		}
		
		saida.close();
		scan.close();
		client.close();*/
	}
}
