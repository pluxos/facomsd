package client.service;

import java.io.PrintStream;
import java.util.Scanner;

import client.commons.utils.CommandsModelator;
import client.service.connector.ProcessUser;
import client.service.validation.CommandRequestValidator;

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

			/* Ler commando */
			String command = CommandsModelator.getCommandModel(scanner.nextLine());

			/* Validar comando */
			try {
				CommandRequestValidator.validateCommand(command);
				
				if(command.equals("sair")){
					break;
				}
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
				continue;
			}
			
			ProcessUser.sendCommand(command, this.output);
			
			/* Mandar comando */

		}

		System.out.println("Finalizando thread de Comandos...");
	}

	private void printCommands(){
		System.out.println("Comandos: ");
		System.out.println("create, read, update e delete");
		System.out.println("Caso queira sair, digite 'sair'");
	}
}
