package cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import utils.Constant;

public class Client implements Runnable {

	static Semaphore mutex = new Semaphore(1);
	ClientResponse clientResponse;
	CommandHandler commandHandler;

	public void run() {
		try {
			Scanner s = new Scanner(System.in);
			
			Socket socket = new Socket(Constant.HOST, Constant.SERVER_PORT);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			String menu = (String) in.readObject();
			commandHandler = new CommandHandler(menu);
			Thread tRead = new Thread(new ClientSend(socket, s, out, menu, commandHandler));
			tRead.start();
			clientResponse = new ClientResponse(in);
			Thread tResponse = new Thread(clientResponse);
			tResponse.start();

			while (true) {
				if (!tRead.isAlive()) {
					Thread.sleep(5000);
					clientResponse.finalizar();
					break;
				}

			}

			System.out.println("ClientResponse finalizado");
			System.exit(0);
		} catch (Exception e) {
		}
	}
}
