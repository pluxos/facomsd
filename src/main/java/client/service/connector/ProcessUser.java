package client.service.connector;

import java.io.PrintStream;
import java.math.BigInteger;

import client.commons.dto.UserDTO;
import client.commons.utils.BatmanCode;

public class ProcessUser {
	
	public static void sendCommand(String command, PrintStream output) {
		/* split */
		String[] commandsArray = command.split("; ");
		
		/* Switch */
		switch(commandsArray[0]) {
			case "create":
				createUser(commandsArray, output);
				break;
			case "get":
				getUser(commandsArray, output);
				break;
			case "update":
				updateUser(commandsArray, output);
				break;
			case "delete":
				deleteUser(commandsArray, output);
				break;
			default:
				System.out.println("Comando Inválido!");
				break;
		}
	}

	private static void createUser(String[] commandsArray, PrintStream output) {
		/* Cria o objeto UserDTO */
		UserDTO user = new UserDTO(commandsArray[2], commandsArray[3], commandsArray[4]);
		/* Obj -> vetor bytes */
		byte[] userByte = BatmanCode.encode(user);
		BigInteger userCode = BatmanCode.stringToBigInteger(commandsArray[1]);
		/* SendCommands -> Enviar */
		SendCommands.sendCreateCommand(commandsArray[0], userCode, userByte, output);
	}
	
	private static void getUser(String[] commandsArray, PrintStream output) {
		BigInteger userCode = BatmanCode.stringToBigInteger(commandsArray[1]);
		
		SendCommands.sendGetCommand(commandsArray[0], userCode, output);
	}
	
	private static void deleteUser(String[] commandsArray, PrintStream output) {
		BigInteger userCode = BatmanCode.stringToBigInteger(commandsArray[1]);
		
		SendCommands.sendDeleteCommand(commandsArray[0], userCode, output);
	}

	private static void updateUser(String[] commandsArray, PrintStream output) {
		UserDTO user = new UserDTO(commandsArray[2], commandsArray[3], commandsArray[4]);
		
		byte[] userByte = BatmanCode.encode(user);
		BigInteger userCode = BatmanCode.stringToBigInteger(commandsArray[1]);
		
		SendCommands.sendUpdateCommand(commandsArray[0], userCode, userByte, output);
	}
}
