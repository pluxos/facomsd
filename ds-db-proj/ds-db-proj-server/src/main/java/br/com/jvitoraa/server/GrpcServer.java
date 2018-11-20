package br.com.jvitoraa.server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.lang3.math.NumberUtils;

import br.com.jvitoraa.grpc.facade.ServerFacade;
import br.com.jvitoraa.grpc.service.LogSnapshotIndexService;
import br.com.jvitoraa.grpc.vo.RangeVO;
import br.com.jvitoraa.queue.controller.QueueController;
import br.com.jvitoraa.queue.runnable.DatabaseQueueProcessor;
import br.com.jvitoraa.queue.runnable.LogQueueProcessor;
import br.com.jvitoraa.queue.runnable.MasterQueueProcessor;
import br.com.jvitoraa.queue.runnable.ServerComunicationProcessor;
import br.com.jvitoraa.queue.runnable.SnapshotProcessor;
import br.com.jvitoraa.recovery.DatabaseRecoveryUtil;
import br.com.jvitoraa.repository.DatabaseRepository;
import lombok.Getter;
import lombok.Setter;

public class GrpcServer {

	private static final String P_QUEUE_SIZE = "queue.size";
	private static final String P_M_VALUE = "m.value";
	private static final String APPLICATION_PROPERTIES_PATH = "./src/main/resources/application.properties";
	private static final String P_SNAPSHOT_TIMER = "snapshot.timer";
	private static final String P_SERVER_PORT = "server.port";
	private static final String P_RECOVER_SNAP_PATH = "recover.snap.path";
	private static final String P_RECOVER_LOG_PATH = "recover.log.path";

	@Getter
	private DatabaseRepository databaseRepository;
	private QueueController queueController;
	private DatabaseRecoveryUtil databaseRecoveryUtil;
	private LogSnapshotIndexService logSnapshotService;
	private Properties properties;
	private RangeVO range;

	@Getter
	@Setter
	private String serverId;
	@Getter
	@Setter
	private String serverPort;
	@Getter
	@Setter
	private String nextServer;
	@Getter
	@Setter
	private String previousServer;
	@Getter
	@Setter
	private String logPath;
	@Getter
	@Setter
	private String snapPath;

	private static final Logger LOGGER = Logger.getLogger(GrpcServer.class.getName());

	public GrpcServer(String serverId) {
		this.serverId = serverId;
	}

	public void start() throws IOException, InterruptedException {

		this.properties = this.propertyReader();
		this.setupServer();
		this.calcRange();
		this.startUp();

	}

	private void startUp() throws IOException, InterruptedException {
		this.databaseRepository = new DatabaseRepository();
		this.queueController = new QueueController(Integer.valueOf(this.properties.getProperty(P_QUEUE_SIZE)));

		LOGGER.info("Server starting on port: " + this.serverPort);
		this.logSnapshotService = new LogSnapshotIndexService();
		this.databaseRecoveryUtil = new DatabaseRecoveryUtil(this.databaseRepository, this.logSnapshotService,
				this.logPath, this.snapPath);
		this.databaseRecoveryUtil.executeRecovery();
		ServerFacade serverFacade = new ServerFacade(Integer.valueOf(this.serverPort), this.queueController);
		serverFacade.startUp();

		startThreads();
		serverFacade.awaitTermination();
	}

	private void startThreads() throws InterruptedException {
		Thread tMaster = new Thread(
				new MasterQueueProcessor(this.queueController, this.range, Integer.valueOf(this.serverId)));
		Thread tDatabase = new Thread(new DatabaseQueueProcessor(this.queueController, this.databaseRepository));
		Thread tLog = new Thread(new LogQueueProcessor(this.queueController, this.logSnapshotService, this.logPath));
		Thread tSnap = new Thread(new SnapshotProcessor(this.logSnapshotService, this.databaseRepository,
				Integer.valueOf(this.properties.getProperty(P_SNAPSHOT_TIMER)), this.snapPath));
		Thread tCommunication = new Thread(new ServerComunicationProcessor(Integer.valueOf(this.getPreviousServer()),
				Integer.valueOf(this.getNextServer()), this.range, this.queueController));
		tMaster.start();
		tDatabase.start();
		tLog.start();
		tSnap.start();
		tCommunication.start();
		tMaster.join();
		tDatabase.join();
		tLog.join();
		tSnap.join();
		tCommunication.join();
	}

	private Properties propertyReader() throws IOException {
		Properties propertyFile = new Properties();
		FileReader fileReader = new FileReader(APPLICATION_PROPERTIES_PATH);
		propertyFile.load(fileReader);
		return propertyFile;
	}

	private void setupServer() {
		String[] listOfPorts = properties.getProperty(P_SERVER_PORT).split(",");
		this.serverPort = listOfPorts[Integer.valueOf(this.serverId)];

		if (Objects.nonNull(this.serverId) && Integer.valueOf(this.serverId) == NumberUtils.INTEGER_ZERO) {
			this.setPreviousServer(listOfPorts[listOfPorts.length + NumberUtils.INTEGER_MINUS_ONE]);
			this.setNextServer(listOfPorts[Integer.valueOf(this.serverId) + NumberUtils.INTEGER_ONE]);
		} else if (Objects.nonNull(this.serverId)
				&& Integer.valueOf(this.serverId) == listOfPorts.length + NumberUtils.INTEGER_MINUS_ONE) {
			this.setPreviousServer(listOfPorts[Integer.valueOf(this.serverId) + NumberUtils.INTEGER_MINUS_ONE]);
			this.setNextServer(listOfPorts[NumberUtils.INTEGER_ZERO]);
		} else {
			this.setPreviousServer(listOfPorts[Integer.valueOf(this.serverId) + NumberUtils.INTEGER_MINUS_ONE]);
			this.setNextServer(listOfPorts[Integer.valueOf(this.serverId) + NumberUtils.INTEGER_ONE]);
		}

		this.setLogPath(properties.getProperty(P_RECOVER_LOG_PATH).replace("X", this.serverId));
		this.setSnapPath(properties.getProperty(P_RECOVER_SNAP_PATH).replace("X", this.serverId));

	}

	private void calcRange() {
		this.range = new RangeVO(properties.getProperty(P_SERVER_PORT).split(",").length,
				Integer.valueOf(properties.getProperty(P_M_VALUE)), Integer.valueOf(this.serverId));
	}

}
