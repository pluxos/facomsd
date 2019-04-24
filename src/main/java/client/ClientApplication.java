package client;

import client.service.ClientCommands;
import client.service.ServerResponse;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {
	public static void main(String[] args) throws IOException {
		Socket client = new Socket("127.0.0.1", 12345);

		PrintStream output = new PrintStream(client.getOutputStream());
		Scanner input = new Scanner(client.getInputStream());
		
		ClientCommands clientCommands = new ClientCommands(output);
		Thread threadCommands = new Thread(clientCommands);

		ServerResponse serverResponse = new ServerResponse(input);
		Thread threadResponse = new Thread(serverResponse);

		threadCommands.start();
		threadResponse.start();

		try {
			threadCommands.join();
			Thread.sleep(5000);

			System.out.println("Finalizando Cliente!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
