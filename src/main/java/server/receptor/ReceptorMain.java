package server.receptor;

import java.net.Socket;

public class ReceptorMain implements Runnable {
	private final Socket socket;

	public ReceptorMain(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		System.out.println("Novo cliente conectado na porta: " + this.socket.getPort());
		System.out.println(Thread.currentThread().getId());
	}
}
