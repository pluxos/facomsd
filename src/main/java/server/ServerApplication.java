package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.receptor.*;

public class ServerApplication {
	
	private static String filePath = "comand.log";

	public static void main(String[] args) throws IOException {
		File file = new File(filePath);
		if(file.exists()){
			Thread t = new Thread(new RecoverLog(new BufferedReader(new FileReader(file))));
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				System.err.println("Erro ao recuperrar LOG");
			}
		}

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