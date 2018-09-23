package servidor.queue;

import servidor.ClientData;

public class QueueF2 extends Queue implements Runnable {

	public QueueF2(QueueCommand queue) {
		super(queue);
	}

	@Override
	public void run() {
		try {
			while (true) {
				ClientData elemento = super.queue.consumeF2();
				System.out.println("Escrevendo no log");
				//escrever elemento log
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
