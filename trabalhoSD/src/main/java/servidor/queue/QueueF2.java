package servidor.queue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import servidor.ClientData;

public class QueueF2 extends Queue implements Runnable {

	public QueueF2(QueueCommand queue) {
		super(queue);
	}

	@Override
	public void run() {
		try {
			System.out.println("Iniciando F2");
			File arquivo = new File("operacoes.log");
			if (!arquivo.exists()) {
				arquivo.createNewFile();
			}
			Path path = Paths.get("operacoes.log");
			while (true) {
				ClientData elemento = super.queue.consumeF2();
				if (elemento.getComando().charAt(0) != '2'
						&& elemento.getComando().charAt(0) != 'r'
						&& elemento.getComando().charAt(0) != 'R') {

					String gravar;
					if (arquivo.length() <= 0) {
						gravar = elemento.getComando();
					} else {
						gravar = System.lineSeparator() + elemento.getComando();
					}
					Files.write(path, gravar.getBytes(), StandardOpenOption.APPEND);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException io) {
			System.out.println("Erro ao escrever em log, finalizando o servidor");
		}
	}

}
