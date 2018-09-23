package servidor.queue;

import java.io.IOException;

import servidor.ClientData;
import servidor.command.ExecuteCommand;

public class QueueF3 extends Queue implements Runnable {
	public QueueF3(QueueCommand queue) {
		super(queue);
	}

	ExecuteCommand execute = new ExecuteCommand();

	@Override
	public void run() {
		try {

			while (true) {
				try {
					// String elemento = QueueCommand.consumeF3();
					ClientData elemento = super.queue.consumeF3();
					String resposta = execute.execute(elemento.getComando());
					System.out.println("comando de F3 executado");
					elemento.getOut().writeObject(resposta);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
