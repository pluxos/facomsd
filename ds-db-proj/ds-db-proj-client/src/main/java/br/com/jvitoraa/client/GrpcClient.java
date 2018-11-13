package br.com.jvitoraa.client;

import java.util.logging.Logger;

import br.com.jvitoraa.facade.ClientFacade;
import br.com.jvitoraa.observer.GrpcObserver;
import br.com.jvitoraa.runnable.ClientInputThread;

public class GrpcClient {
	
	private static final Logger LOGGER = Logger.getLogger(GrpcClient.class.getName());
	
	public void start() throws InterruptedException {
		
		LOGGER.info("Starting up client");
		ClientFacade clientFacade = new ClientFacade(8001);
		GrpcObserver observer = new GrpcObserver();
		
		Thread t = new Thread(new ClientInputThread(clientFacade, observer));
		t.start();
		t.join();
		
	}

}
