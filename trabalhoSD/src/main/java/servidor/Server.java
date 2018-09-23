package servidor;

import java.net.ServerSocket;

public class Server {
	//static Semaphore mutex = new Semaphore(1);
	ServerSocket serverSocket = null;

	public void iniciar() {
		try {
			serverSocket = new ServerSocket(9876);
			while (true) {
				new DadosHandlerThread(serverSocket.accept()).start();
			}
		} catch (Exception e) {
		}
	}
}