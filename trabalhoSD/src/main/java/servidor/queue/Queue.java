package servidor.queue;

import java.util.concurrent.Semaphore;

import servidor.Finger;
import servidor.dataBase.Data;

public class Queue {
	protected QueueCommand queue;
	protected Data data;
	protected Finger finger;
	protected Semaphore mutex_f1;
	protected Semaphore mutex;

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

	public Queue(QueueCommand queue, Data data, Finger finger, Semaphore mutex_f1, Semaphore mutex) {
		this.data = data;
		this.queue = queue;
		this.finger = finger;
		this.mutex_f1 = mutex_f1;
		this.mutex = mutex;
	}

	public void run() throws InterruptedException {
		Thread f1 = new Thread(new QueueF1(queue));
		Thread f2 = new Thread(new QueueF2(queue, finger));
		Thread f3 = new Thread(new QueueF3(queue, data));
		Thread f4 = new Thread(new QueueF4(queue, finger));
		f1.start();
		f2.start();
		f3.start();
		f4.start();
	}
}
