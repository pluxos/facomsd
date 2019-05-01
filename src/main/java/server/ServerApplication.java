package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.receptor.*;

public class ServerApplication {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = SocketConnection.getServerSocket();
		System.out.println("Server TCP startado na porta 12345");
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (;;) {
			try {
				pool.execute(new ReceptorMain(serverSocket.accept()));
			} catch (IOException e) {
				pool.shutdown();
				break;
			}
		}
		serverSocket.close();
	}
}