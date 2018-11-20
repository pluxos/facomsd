package servidor.queue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import servidor.ClientData;
import servidor.Finger;
import servidor.ServerClass;

public class QueueF2 extends Queue implements Runnable {
	public QueueF2(QueueCommand queue, Finger finger) {
		super(queue, finger);
	}

	@Override
	public void run() {
		try {
			System.out.println("Iniciando F2");

			File arquivo = new File("logs\\" + super.finger.getId() + "\\operacoes.log");
			if (!arquivo.exists()) {
				arquivo.createNewFile();
			}
			PrintStream fileStream = new PrintStream(
					new FileOutputStream("logs\\" + super.finger.getId() + "\\operacoes.log", true));
			while (true) {
				System.out.println("esperando comando para gravar em log");
				ClientData elemento = super.queue.consumeF2();
				System.out.println("gravando: "+ elemento.getComando());
				String comando = elemento.getComando().split(" ")[0];
				if (!comando.equals("read") && !comando.equals("2")) {
					fileStream.append(elemento.getComando() + System.getProperty("line.separator"));
				}
				
			//	ServerClass.mutex.release();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException io) {
			System.out.println("Erro ao escrever em log");
		}
	}
}
