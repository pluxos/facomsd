package br.com.jvitoraa.server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import br.com.jvitoraa.grpc.facade.ServerFacade;
import br.com.jvitoraa.grpc.service.LogSnapshotIndexService;
import br.com.jvitoraa.queue.controller.QueueController;
import br.com.jvitoraa.queue.runnable.DatabaseQueueProcessor;
import br.com.jvitoraa.queue.runnable.LogQueueProcessor;
import br.com.jvitoraa.queue.runnable.MasterQueueProcessor;
import br.com.jvitoraa.queue.runnable.SnapshotProcessor;
import br.com.jvitoraa.recovery.DatabaseRecoveryUtil;
import br.com.jvitoraa.repository.DatabaseRepository;

public class GrpcServer {

	private static final String P_SNAPSHOT_TIMER = "snapshot.timer";
	private static final String P_SERVER_PORT = "server.port";
	private static final String P_RECOVER_SNAP_PATH = "recover.snap.path";
	private static final String P_RECOVER_LOG_PATH = "recover.log.path";

	private DatabaseRepository databaseRepository;
	private QueueController queueController;
	private DatabaseRecoveryUtil databaseRecoveryUtil;
	private LogSnapshotIndexService logSnapshotService;
	private Properties properties;

	private static final Logger LOGGER = Logger.getLogger(GrpcServer.class.getName());

	public void start() throws IOException, InterruptedException {

		this.startUp();

	}

	private void startUp() throws IOException, InterruptedException {
		this.properties = this.propertyReader();
		this.databaseRepository = new DatabaseRepository();
		this.queueController = new QueueController();

		LOGGER.info("Server starting on port: " + properties.getProperty(P_SERVER_PORT));
		this.logSnapshotService = new LogSnapshotIndexService();
		this.databaseRecoveryUtil = new DatabaseRecoveryUtil(this.databaseRepository, this.logSnapshotService,
				properties.getProperty(P_RECOVER_LOG_PATH), properties.getProperty(P_RECOVER_SNAP_PATH));
		this.databaseRecoveryUtil.executeRecovery();
		ServerFacade serverFacade = new ServerFacade(Integer.valueOf(properties.getProperty(P_SERVER_PORT)),
				this.queueController);
		serverFacade.startUp();

		startThreads();
		serverFacade.awaitTermination();
	}

	private void startThreads() throws InterruptedException {
		Thread tMaster = new Thread(new MasterQueueProcessor(this.queueController));
		Thread tDatabase = new Thread(new DatabaseQueueProcessor(this.queueController, this.databaseRepository));
		Thread tLog = new Thread(new LogQueueProcessor(this.queueController, this.logSnapshotService,
				this.properties.getProperty(P_RECOVER_LOG_PATH)));
		Thread tSnap = new Thread(new SnapshotProcessor(this.logSnapshotService, this.databaseRepository,
				Integer.valueOf(this.properties.getProperty(P_SNAPSHOT_TIMER)),
				this.properties.getProperty(P_RECOVER_SNAP_PATH)));
		tMaster.start();
		tDatabase.start();
		tLog.start();
		tSnap.start();
		tMaster.join();
		tDatabase.join();
		tLog.join();
		tSnap.join();
	}

	private Properties propertyReader() throws IOException {
		Properties propertyFile = new Properties();
		FileReader fileReader = new FileReader("./src/main/resources/application.properties");
		propertyFile.load(fileReader);
		return propertyFile;
	}

}
