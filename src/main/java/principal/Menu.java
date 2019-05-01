package principal;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private static final List<String> VALID_COMMANDS = Arrays.asList(
			"create",
			"read",
			"update",
			"delete"
			);
	
	public String presentMenu() {
		return 
			"-----Command Menu-----\n"+
			"1 - create\n"+
			"2 - read\n"+
			"3 - update\n"+
			"4 - delete\n"+
			"Type 'exit' for quit.";
	}
	
	public boolean validateCommand(String input) {
		Scanner s = new Scanner(System.in);
		String input2 = s.nextLine();
		if(input.contains("exit") || input.contains("sair")) {
		}
		if(VALID_COMMANDS.stream().anyMatch(str -> str.trim().toLowerCase().equals(input))) {
			System.out.println(input + " is a valid command.");
			return true;
		}
		System.out.println(input + " is not a valid command. Disgarding...");
		return false;
	}
	
	
	
	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.presentMenu();
	}

}
