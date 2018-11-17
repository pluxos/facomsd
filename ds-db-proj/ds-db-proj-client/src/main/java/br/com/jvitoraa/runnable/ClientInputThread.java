package br.com.jvitoraa.runnable;

import java.util.Scanner;
import java.util.logging.Logger;

import br.com.jvitoraa.facade.ClientFacade;
import br.com.jvitoraa.observer.GrpcObserver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientInputThread implements Runnable {

	private ClientFacade clientFacade;
	private GrpcObserver observer;

	private static final Logger LOGGER = Logger.getLogger(ClientFacade.class.getName());

	@Override
	public void run() {
		while (true) {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);

			String instruction = scanner.nextLine();

			if (instruction.equals("sair")) {
				scanner.close();
				try {
					clientFacade.stop();
				} catch (InterruptedException e) {
					LOGGER.warning(e.getMessage());
				}
				break;
			}

			String[] splitedInst = instruction.split("\\s++");

			if (Integer.valueOf(splitedInst[1]) > 255) {
				System.out.println("Comando Inválido! ID maior do que a faixa aceitável!");
			} else {

				switch (splitedInst[0].toUpperCase()) {
				case "CREATE":
					clientFacade.create(Long.valueOf(splitedInst[1]), splitedInst[2], observer);
					break;
				case "READ":
					clientFacade.read(Long.valueOf(splitedInst[1]), observer);
					break;
				case "UPDATE":
					clientFacade.update(Long.valueOf(splitedInst[1]), splitedInst[2], observer);
					break;
				case "DELETE":
					clientFacade.delete(Long.valueOf(splitedInst[1]), observer);
					break;
				default:
					System.out.println("Comando Inválido!");
				}
			}

		}

	}

}
