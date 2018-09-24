package cliente;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend implements Runnable {
	Socket socket;
	Scanner s;
	ObjectOutputStream out;
	String input = "";
	String menu;
	CommandHandler commandHandler;

	public ClientSend(Socket socket, Scanner s, ObjectOutputStream out, String menu, CommandHandler commandHandler) {
		this.socket = socket;
		this.s = s;
		this.out = out;
		this.menu = menu;
		this.commandHandler = commandHandler;

	}

	public void run() {

		try {
			while (!input.toLowerCase().equals("sair")) {

				Client.mutex.acquire();

				System.out.println("Comandos Disponiveis:\n" + menu);
				System.out.println("Digite o comando ou 'sair' para sair ");
				while (true) {
					input = s.nextLine();
					if (input.toLowerCase().equals("sair"))
						break;
					if (commandHandler.checkComand(input)) {
						out.writeObject(input);
						break;
					}
					else
						System.out.println("Comando invalido, digite um comando valido");
				}
			}

			System.out.println("ClientSend finalizado");
			return;

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
