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
			while (true) {
				ClientData elemento = super.queue.consumeF2();
				if (elemento.getComando().charAt(0) != '2'
						&& elemento.getComando().charAt(0) != 'r'
						&& elemento.getComando().charAt(0) != 'R') {
					System.out.println("Escrevendo no log");
					File arquivo = new File("operacoes.log");
					if (!arquivo.exists()) {
						try {
							arquivo.createNewFile();
						} catch (IOException e) {
							try {
								String erro = "Erro ao criar arquivo de log, finalizando conexão";
								System.out.println(erro);
								elemento.getOut().writeObject(erro);
							} catch (IOException ex) {
								System.out
										.println("Falha ao enviar mensagem para o cliente!");
							}
							System.exit(0);
						}
					}
					try {
						Path path = Paths.get("operacoes.log");
						String gravar;
						if (arquivo.length() <= 0) {
							gravar = elemento.getComando();
						} else {
							gravar = System.lineSeparator()
									+ elemento.getComando();
						}
						Files.write(path, gravar.getBytes(),
								StandardOpenOption.APPEND);
					} catch (IOException e) {
						try {
							String erro = "Erro ao escrever no arquivo de log, finalizando conexão";
							System.out.println(erro);
							elemento.getOut().writeObject(erro);
						} catch (IOException ex) {
							System.out
									.println("Falha ao enviar mensagem para o cliente!");
						}
						System.exit(0);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
