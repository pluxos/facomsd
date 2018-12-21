package br.com.thread;

import br.com.atomix.Atomix;

import java.net.ServerSocket;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRecieveThread implements Runnable {

	private ServerSocket serverSocket;
	
	private Queue< String > queue;

	
	private ExecutorService executor = Executors.newCachedThreadPool();


	public ServerRecieveThread( ServerSocket serverSocket, Queue< String > queue ) {
		this.serverSocket = serverSocket;
		this.queue = queue;
	}

	@Override
	public void run() {

			while (true) {
				try {

					String instruction = queue.poll();

					if (instruction != null) {
						Atomix atomix = new Atomix();
						atomix.getReplica();

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
