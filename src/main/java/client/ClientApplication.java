package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import client.business.ClientCommands;
import client.business.ServerResponse;
import client.commons.exceptions.ErrorMap;
import client.commons.utils.SocketConnection;

public class ClientApplication {
	
	public static void main(String[] args) {
		Socket client = null;
		PrintStream output = null;
		Scanner input = null;
		try {
			client = SocketConnection.getSocket();
			output = new PrintStream(client.getOutputStream());
			input = new Scanner(client.getInputStream());
		} catch (IOException e) {
			System.err.println(ErrorMap.CONNECTION_ERROR.getMessage());
			return;
		}
		boolean isTest = args[0] != null && args[0].equals("teste");
		ClientCommands clientCommands = new ClientCommands(output, isTest, args[1]);
		Thread threadCommands = new Thread(clientCommands);

		ServerResponse serverResponse = new ServerResponse(input);
		Thread threadResponse = new Thread(serverResponse);

		threadCommands.start();
		threadResponse.start();

		try {
			threadCommands.join();
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.err.println(ErrorMap.UNEXPECTED_ERROR);
		}
		try {
			client.close();
		} catch (IOException e) {
			System.err.println(ErrorMap.UNEXPECTED_ERROR);
		}
	}
}