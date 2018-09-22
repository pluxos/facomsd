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

				Dados resposta = (Dados) in.readObject();
				System.out.println("Resposta:   " + resposta.getTexto());
				Client.mutex.release();

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
