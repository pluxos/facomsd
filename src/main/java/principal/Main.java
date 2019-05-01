package principal;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.presentMenu();

	}
	
	public void presentMenu() {
		
		List<String> validCommands = Arrays.asList(
			"com1",
			"com2"
		);
		
		new Thread() {
			@Override
			public void run() {
				while(true) {
					System.out.println("-----Command Menu-----");
					System.out.println("1 - Command 1");
					System.out.println("2 - Command 2");
					System.out.println("2 - Command 3");	
					System.out.println("Type 'exit' for quit.");
					Scanner s = new Scanner(System.in);
					String input = s.nextLine();
					if(input.contains("exit") || input.contains("sair")) {
						break;
					}
					if(validCommands.stream().anyMatch(str -> str.trim().toLowerCase().equals(input))) {
						System.out.println(input + " is a valid command. Sending it to the server...");
					}
					else {
						System.out.println(input + " is not a valid command. Disgarding...");
					}
				}
				
			}
		}.start();
	}
	
	public void receiveResponseFromServer() {
		
	}

}
