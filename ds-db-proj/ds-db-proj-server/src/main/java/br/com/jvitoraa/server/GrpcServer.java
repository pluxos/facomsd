package br.com.jvitoraa.server;

import java.io.IOException;
import java.util.logging.Logger;

import br.com.jvitoraa.grpc.facade.ServerFacade;
import br.com.jvitoraa.queue.controller.QueueController;
import br.com.jvitoraa.queue.runnable.DatabaseQueueProcessor;
import br.com.jvitoraa.queue.runnable.LogQueueProcessor;
import br.com.jvitoraa.queue.runnable.MasterQueueProcessor;
import br.com.jvitoraa.recovery.DatabaseRecoveryUtil;
import br.com.jvitoraa.repository.DatabaseRepository;

public class GrpcServer {
	
	private DatabaseRepository databaseRepository;
	private QueueController queueController;
	private DatabaseRecoveryUtil databaseRecoveryUtil;
	
	private static final Logger LOGGER = Logger.getLogger(GrpcServer.class.getName());
	
    public void start() throws IOException, InterruptedException {
    	
    	this.startUp();

    }
	
    private void startUp() throws IOException, InterruptedException {
    	databaseRepository = new DatabaseRepository();
    	queueController = new QueueController();
    	LOGGER.info("Server starting!");
    	databaseRecoveryUtil = new DatabaseRecoveryUtil(databaseRepository);
    	databaseRecoveryUtil.recoverFromLog();
    	ServerFacade serverFacade = new ServerFacade(8001, queueController);
    	serverFacade.startUp();
    	
    	Thread tMaster = new Thread(new MasterQueueProcessor(queueController));
    	Thread tDatabase = new Thread(new DatabaseQueueProcessor(queueController, databaseRepository));
    	Thread tLog = new Thread(new LogQueueProcessor(queueController));
    	tMaster.start();
    	tDatabase.start();
    	tLog.start();
    	tMaster.join();
    	tDatabase.join();
    	tLog.join();
    	serverFacade.awaitTermination();
    }

}
