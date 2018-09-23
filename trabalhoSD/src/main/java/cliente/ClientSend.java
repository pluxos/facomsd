package cliente;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend implements Runnable {
	Socket socket;
	Scanner s;
	ObjectOutputStream out;
	String input;
	String menu;

	public ClientSend(Socket socket, Scanner s, ObjectOutputStream out, String menu) {
		this.socket = socket;
		this.s = s;
		this.out = out;
		this.menu = menu;
				

	}

	public void run() {

		try {
			while (!s.equals("exit")) {
				
				Client.mutex.acquire();
				
				System.out.println("Comandos Disponiveis:\n"+menu);
				System.out.println("Digite o comando ");
				input = s.nextLine();
				out.writeObject(input);
				
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
