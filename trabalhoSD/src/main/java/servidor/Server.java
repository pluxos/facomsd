package servidor;

import java.net.ServerSocket;

import servidor.dataBase.Data;
import servidor.queue.Queue;
import servidor.queue.QueueCommand;
import utils.Constant;

public class Server {
	// static Semaphore mutex = new Semaphore(1);
	private ServerSocket serverSocket = null;
	private QueueCommand queueCommand = null;
	private Queue queue = null;
	
	// QueueCommand queue = new QueueCommand();

	public void iniciar() {
		try {
			serverSocket = new ServerSocket(Constant.SERVER_PORT);
			queueCommand = new QueueCommand();
			Data.recovery();
			queue = new Queue(queueCommand);
			queue.run();

			while (true) {
				new HandlerThreadServer(serverSocket.accept(), queueCommand).start();
			}
		} catch (Exception e) {
		}
	}
}