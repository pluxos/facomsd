package cliente;

import java.io.ObjectInputStream;
import java.net.Socket;

import servidor.Dados;

public class ClientResponse implements Runnable {
	Socket socket;
	ObjectInputStream in;

	public ClientResponse(Socket socket, ObjectInputStream in) {
		this.socket = socket;
		this.in = in;
	}

	public void run() {
		try {
			while (true) {

				String resposta = (String) in.readObject();
				System.out.println(">>>>> " + resposta + "\n\n");
				Client.mutex.release();

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
