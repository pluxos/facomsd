package br.com.thread;

import java.net.ServerSocket;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRecieveThread implements Runnable {

	private ServerSocket serverSocket;
	
	private Queue< String > Queue;

	
	private ExecutorService executor = Executors.newCachedThreadPool();


	public ServerRecieveThread( ServerSocket serverSocket, Queue< String > Queue ) {
		this.serverSocket = serverSocket;
		this.Queue = Queue;
	}

	@Override
	public void run() {

			while (true) {
				try {

					String instruction = Queue.poll();

					if (instruction != null) {

						synchronized (GrpcThread.f1) {
							GrpcThread.f1.add(instruction);
						}

					}

					Thread.sleep(1);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}

}
