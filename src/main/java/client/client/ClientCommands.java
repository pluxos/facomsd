package client.client;

import java.io.PrintStream;
import java.util.Scanner;

import client.commons.utils.CommandsModelator;

public class ClientCommands implements Runnable {

	private PrintStream output;
	private Scanner scanner;
	
	public ClientCommands(PrintStream output){
		this.output = output;
		this.scanner = new Scanner(System.in);
	}
	
	@Override
	public void run() {
		for(;;){
			this.printCommands();

			String command = CommandsModelator.getStringLowerCase(scanner.nextLine());

			if(command.equals("sair")){
				break;
			}
		}

		System.out.println("Finalizando thread de Comandos...");
	}

	private void printCommands(){
		System.out.println("Comandos: ");
		System.out.println("create, read, update e delete");
		System.out.println("Caso queira sair, digite 'sair'");
	}
}
