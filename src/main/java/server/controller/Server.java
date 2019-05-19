package server.controller;

import server.commons.utils.UserControl;
import server.receptor.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

	private String filePath;

	public Server(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void run() {
		File file = new File(filePath);
		if (file.exists()) {
			try {
				Thread t = new Thread(new RecoverLog(new BufferedReader(new FileReader(file))));
				t.start();
				t.join();
			} catch (InterruptedException e) {
				System.err.println("Erro ao recuperrar LOG");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		ServerSocket serverSocket = null;
		try {
			serverSocket = SocketConnection.getServerSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				Socket s = serverSocket.accept();
				if( UserControl.startThread()) {
					UserControl.newUser();
					pool.execute(new ReceptorMain(s));
				} else {
					s.close();
				}
			} catch (IOException e) {
				pool.shutdown();
				break;
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {

		}
	}
}
