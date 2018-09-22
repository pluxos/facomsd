package cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend implements Runnable {
	Socket socket;
	Scanner s;
	ObjectOutputStream out;
	Long numero;
	String menu;

	public ClientSend(Socket socket, Scanner s, ObjectOutputStream out, String menu) {
		this.socket = socket;
		this.s = s;
		this.out = out;
		this.menu = menu;
				

	}

	public void run() {

		try {
			while (true) {
				
				Client.mutex.acquire();
				
				System.out.println("Comandos Disponiveis:\n"+menu);
				System.out.println("Digite o comando ");
				numero = s.nextLong();
				out.writeObject(numero);
				
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
