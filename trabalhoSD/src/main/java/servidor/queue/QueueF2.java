package servidor.queue;

import servidor.ClientData;

public class QueueF2 extends Queue implements Runnable {

	public QueueF2(QueueCommand queue) {
		super(queue);
	}

	@Override
	public void run() {
		try {
		  System.out.println("Iniciando F2");
			while (true) {
				ClientData elemento = super.queue.consumeF2();
				System.out.println("Escrevendo no log");
				//escrever elemento log
			}
		} catch (InterruptedException e) {
		  System.out.println("Erro, f2 finalizada");
		}
	}

}
