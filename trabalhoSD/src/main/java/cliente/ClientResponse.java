package cliente;

import java.io.ObjectInputStream;
import java.net.Socket;

import servidor.Dados;

public class ClientResponse implements Runnable {
	// private Socket socket;
	private ObjectInputStream in;
	private boolean running;

	public ClientResponse(ObjectInputStream in) {
		// this.socket = socket;
		this.in = in;
		this.running = true;
	}

	public void finalizar() {
		this.running = false;
	}

	public void run() {
		try {
			while (this.running) {

				String resposta = (String) in.readObject();
				System.out.println(">>>>> " + resposta + "\n\n");
				Client.mutex.release();

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
