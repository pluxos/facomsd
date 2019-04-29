package client.business;

import java.io.PrintStream;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import client.business.validation.InputRequestValidator;
import client.commons.domain.Method;
import client.commons.exceptions.DomainException;
import client.commons.exceptions.ErrorMap;
import client.commons.utils.CommandUtils;

public class ClientCommands implements Runnable {

	private PrintStream output;
	private Scanner scanner;

	public ClientCommands(PrintStream output) {
		this.output = output;
		this.scanner = new Scanner(System.in);
	}

	@Override
	public void run() {
		for (;;) {
			printCommands();
			String userInput = CommandUtils.modelateRequest(scanner.nextLine());
			try {
				InputRequestValidator.validateInput(userInput);
				if (CommandUtils.getMethodByUserInput(userInput).equals(Method.SAIR)) {
					break;
				}
				UserProcessor.sendCommand(userInput, this.output);
			} catch (DomainException e) {
				System.err.println(e.getErrorMessage());
			} catch (Exception e) {
				System.err.println(ErrorMap.UNEXPECTED_ERROR);
			}
		}
		System.out.println("Finalizando thread de Comandos...");
	}

	private void printCommands() {
		System.out.println("Comandos: ");
		System.out.println("create, read, update e delete");
		System.out.println("Caso queira sair, digite 'sair'");
	}
}
