package servidor.queue;

import servidor.ClientData;

public class QueueF1 extends Queue implements Runnable {

	public QueueF1(QueueCommand queue) {
		super(queue);
	}

	@Override
	public void run() {

		try {
			while (true) {
				ClientData elemento = super.queue.consumeF1();
				System.out.println("copiando para F2 e F3");
				super.queue.produceF2(elemento);
				super.queue.produceF3(elemento);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
