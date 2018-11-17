package servidor;

import servidor.dataBase.Data;
import servidor.queue.QueueCommand;
import servidor.queue.QueueF1;
import servidor.queue.QueueF2;
import servidor.queue.QueueF3;
import servidor.queue.QueueF4;

public class Queue {
	protected QueueCommand queue;
	protected Data data;
	protected Finger finger;

	public Queue(QueueCommand queue) {
		this.queue = queue;
	}

	public Queue(QueueCommand queue, Data data) {
		this.queue = queue;
		this.data = data;
	}

	public Queue(QueueCommand queue, Finger finger) {
		this.queue = queue;
		this.finger = finger;
	}

	public Queue(QueueCommand queue, Data data, Finger finger) {
		this.data = data;
		this.queue = queue;
		this.finger = finger;
	}

	public void run() throws InterruptedException {
		Thread f1 = new Thread(new QueueF1(queue));
		Thread f2 = new Thread(new QueueF2(queue));
		Thread f3 = new Thread(new QueueF3(queue, data));
		Thread f4 = new Thread(new QueueF4(queue, finger));
		f1.start();
		f2.start();
		f3.start();
		f4.start();
	}
}
