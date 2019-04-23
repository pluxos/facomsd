package client;

import client.commons.validation.CommandsValidator;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientCommands implements Runnable {

	private PrintStream output;
	private Scanner scanner;
	
	public ClientCommands(PrintStream output){
		this.output = output;
		this.scanner = new Scanner(System.in);
	}
	
	@Override
	public void run() {
		System.out.println("Thread de comando Iniciado!");

		while(scanner.hasNextLine()){
			String command = scanner.nextLine();

			CommandsValidator.validateCommand(command);
		}
	}
}
