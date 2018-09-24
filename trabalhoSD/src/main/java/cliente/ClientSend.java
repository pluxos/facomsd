package cliente;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend implements Runnable {
	Socket socket;
	Scanner s;
	ObjectOutputStream out;
	String input="";
	String menu;

	public ClientSend(Socket socket, Scanner s, ObjectOutputStream out, String menu) {
		this.socket = socket;
		this.s = s;
		this.out = out;
		this.menu = menu;

	}

	public void run() {

		try {
			while (!input.equals("sair")) {

				Client.mutex.acquire();

				System.out.println("Comandos Disponiveis:\n" + menu);
				System.out.println("Digite o comando ou 'sair' para sair ");
				input = s.nextLine();
				if (input.toLowerCase().equals("sair"))
					break;
				out.writeObject(input);

			}
			
			System.out.println("ClientSend finalizado");
			return;

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
