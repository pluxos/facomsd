package servidor;

import servidor.dataBase.Data;
import servidor.queue.QueueCommand;
import servidor.queue.QueueF1;
import servidor.queue.QueueF2;
import servidor.queue.QueueF3;

public class Queue {
	protected QueueCommand queue;
	protected Data data;

	public Queue(QueueCommand queue) {
		this.queue = queue;
	}

	public Queue(QueueCommand queue, Data data) {
		this.queue = queue;
		this.data = data;
	}

	public void run() throws InterruptedException {
		Thread f1 = new Thread(new QueueF1(queue));
		Thread f2 = new Thread(new QueueF2(queue));
		Thread f3 = new Thread(new QueueF3(queue, data));
		f1.start();
		f2.start();
		f3.start();
	}
}
