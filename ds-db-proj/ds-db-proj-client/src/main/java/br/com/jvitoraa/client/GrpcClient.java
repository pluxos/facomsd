package br.com.jvitoraa.client;

import java.util.Scanner;
import java.util.logging.Logger;

import br.com.jvitoraa.facade.ClientFacade;
import br.com.jvitoraa.observer.GrpcObserver;
import br.com.jvitoraa.runnable.ClientInputThread;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class GrpcClient {
	
	public GrpcClient(Integer serverPort) {
		this.serverPort = serverPort;
		this.scanner = new Scanner(System.in);
	}
	
	@Setter
	private Integer serverPort;
	@Getter
	private Scanner scanner;
	
	Thread tInput;
	
	private static final Logger LOGGER = Logger.getLogger(GrpcClient.class.getName());
	
	public void start() throws InterruptedException {
		
		LOGGER.info("Starting up client");
		ClientFacade clientFacade = new ClientFacade(this.serverPort);
		GrpcObserver observer = new GrpcObserver();
		
		tInput = new Thread(new ClientInputThread(clientFacade, observer, scanner, this));
		tInput.start();
		tInput.join();
		
	}
	
	public void stop(ClientFacade facade) throws InterruptedException {
		facade.stop();
	}
}
