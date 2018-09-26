package servidor.queue;

import servidor.ClientData;
import servidor.Server;

public class QueueF1 extends Queue implements Runnable {

	public QueueF1(QueueCommand queue) {
		super(queue);
	}

	@Override
	public void run() {

		try {
		  System.out.println("Iniciando F1");
			while (true) {
				ClientData elemento = super.queue.consumeF1();
				Server.mutex_f1.acquire();
				super.queue.produceF2(elemento);
				super.queue.produceF3(elemento);
				Server.mutex_f1.release();
			}
			
		} catch (InterruptedException e) {
		  System.out.println("Erro, f1 finalizada");
		}

	}

}
