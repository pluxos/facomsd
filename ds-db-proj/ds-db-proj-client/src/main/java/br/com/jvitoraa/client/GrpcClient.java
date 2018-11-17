package br.com.jvitoraa.client;

import java.util.logging.Logger;

import br.com.jvitoraa.facade.ClientFacade;
import br.com.jvitoraa.observer.GrpcObserver;
import br.com.jvitoraa.runnable.ClientInputThread;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class GrpcClient {
	
	@Setter
	private Integer serverPort;
	
	private static final Logger LOGGER = Logger.getLogger(GrpcClient.class.getName());
	
	public void start() throws InterruptedException {
		
		LOGGER.info("Starting up client");
		ClientFacade clientFacade = new ClientFacade(this.serverPort);
		GrpcObserver observer = new GrpcObserver();
		
		Thread t = new Thread(new ClientInputThread(clientFacade, observer));
		t.start();
		t.join();
		
	}

}
