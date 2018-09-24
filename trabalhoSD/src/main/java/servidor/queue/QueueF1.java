package servidor.queue;

import servidor.ClientData;

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
				super.queue.produceF2(elemento);
				super.queue.produceF3(elemento);
				System.out.println("consumido de F1 e copiando para F2 e F3");
			}
			
		} catch (InterruptedException e) {
		  System.out.println("Erro, f1 finalizada");
		}

	}

}
