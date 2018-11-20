package br.com.jvitoraa.runnable;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

import br.com.jvitoraa.client.GrpcClient;
import br.com.jvitoraa.facade.ClientFacade;
import br.com.jvitoraa.observer.GrpcObserver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientInputThread implements Runnable {

	private ClientFacade clientFacade;
	private GrpcObserver observer;
	private Properties properties;
	private Scanner scanner;
	private GrpcClient client;

	private static final String APPLICATION_PROPERTIES_PATH = "./src/main/resources/application.properties";
	private static final String P_M_VALUE = "m.value";

	private static final Logger LOGGER = Logger.getLogger(ClientFacade.class.getName());

	public ClientInputThread(ClientFacade clientFacade, GrpcObserver observer, Scanner scanner, GrpcClient client) {
		this.clientFacade = clientFacade;
		this.observer = observer;
		this.scanner = scanner;
		this.client = client;
	}

	@Override
	public void run() {
		try {
			this.properties = this.propertyReader();
		} catch (IOException e1) {
			LOGGER.warning(e1.getMessage());
		}
		while (true) {

			String instruction = scanner.nextLine();

			if (instruction.equals("sair")) {
				scanner.close();
				try {
					client.stop(clientFacade);
				} catch (InterruptedException e) {
					LOGGER.warning(e.getMessage());
				}
				break;
			}

			String[] splitedInst = instruction.split("\\s++");

			if (Integer.valueOf(splitedInst[1]) > this.getMaxValue() || Integer.valueOf(splitedInst[1]) < 0) {
				System.out.println("Comando Inválido! ID fora da faixa aceitável!");
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

	private Properties propertyReader() throws IOException {
		Properties propertyFile = new Properties();
		FileReader fileReader = new FileReader(APPLICATION_PROPERTIES_PATH);
		propertyFile.load(fileReader);
		return propertyFile;
	}

	private Integer getMaxValue() {
		return (int) (Math.pow(2, Integer.valueOf(properties.getProperty(P_M_VALUE))) - 1);
	}

}
