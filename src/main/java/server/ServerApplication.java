package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import server.receptor.*;

public class ServerApplication {

	public static void main(String[] args) throws IOException {
		Logger.getLogger(ServerApplication.class).info("Teste!");
		ServerSocket serverSocket = SocketConnection.getServerSocket();
		System.out.println("Server TCP startado na porta 12345");
		
		Thread tConsumer = new Thread(new ConsumerF1());
		Thread tCommand = new Thread(new ThreadCommand());
		Thread tLog = new Thread(new ThreadLog());
		tConsumer.start();
		tCommand.start();
		tLog.start();

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