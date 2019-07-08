package server.receptor;

import io.atomix.utils.net.Address;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import server.business.consumers.CommandExecutorThread;
import server.business.consumers.LogPersistentThread;
import server.business.consumers.OrchestratorThread;
import server.business.consumers.ServerConnectorThread;
import server.business.persistence.Manipulator;
import server.business.persistence.recovery.LogRecovererThread;
import server.business.persistence.routine.Counter;
import server.business.persistence.routine.FileRoutineThread;
import server.commons.atomix.ClusterAtomix;
import server.commons.chord.ChodNode;
import server.commons.chord.Chord;
import server.commons.chord.FingerTable;
import server.commons.utils.FileUtils;
import server.receptor.hooks.ShutdownHook;
import server.requester.GrpcCommunication;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.Executors;

public class ServerThread implements Runnable {

	private String logDirectory;
	private Server server;
	private String chordIp = null;
	private int chordPort;
	private List<Address> addresses;
	private int myId;

	/*
	* args: logs/Server0/ 12345 0 127.0.0.1 5000 127.0.0.1 5002 127.0.0.1 5004
	* */

	public ServerThread(String[] args) {
		Chord.setChodNode(new ChodNode());

		this.logDirectory = args[0];

		this.myId = Integer.parseInt(args[2]);
		this.addresses = new LinkedList<>();

		for(int i = 3; i <args.length; i+=2)
		{
			Address address = new Address(args[i], Integer.parseInt(args[i+1]));
			this.addresses.add(address);
		}

		Chord.getChodNode().setIp(this.addresses.get(myId).host());
		Chord.getChodNode().setPort(Integer.parseInt(args[1]));

		if(args.length == 11) {
			this.chordIp = args[9];
			this.chordPort = Integer.parseInt(args[10]);
			GrpcCommunication.ip = args[9];
			GrpcCommunication.port = this.chordPort;
		}

		Counter.startCounter(args[0]);
	}

	public void stop() {
		if (this.server != null) {
			this.server.shutdown();
		}
	}

	@Override
	public void run() {
		try {
			Thread t = new Thread(new LogRecovererThread(this.logDirectory));
			t.start();
			t.join();
		} catch (InterruptedException e) {
			System.err.println("Erro ao recuperrar LOG");
		}

		try {
			ClusterAtomix.setCluster(this.addresses, this.myId).start().join();

			System.out.println("Cluster joined");

			this.server = this.initGrpcServer();

			System.out.println("Server started, listening on " + Chord.getChodNode().getPort());

			Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(this)));

			ClusterAtomix.initVars();
			Chord.setFt(new FingerTable());

			Chord.getChodNode().setRange(ClusterAtomix.getRange());
			Chord.getChodNode().setKey(ClusterAtomix.getKey().get());
			Manipulator.setDb(ClusterAtomix.getDb());

			ClusterAtomix.getKey().addListener(event -> Chord.getChodNode().setKey(event.newValue()));

			if(this.myId == 0) {
				if( this.chordIp != null){
					this.entryChord();
				} else {
					firstServer();
				}
			}

			startSnapshotRoutine();
			Thread tConsumer = new Thread(new OrchestratorThread());
			Thread tCommand = new Thread(new CommandExecutorThread());
			Thread tLog = new Thread(new LogPersistentThread(this.logDirectory));
			Thread tConsumer4 = new Thread(new ServerConnectorThread());
			tConsumer.start();
			tCommand.start();
			tLog.start();
			tConsumer4.start();

			Thread utils = new Thread(new UtilsCommands());
			utils.start();

			this.server.awaitTermination();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Server initGrpcServer() throws IOException {
		return ServerBuilder.forPort(Chord.getChodNode().getPort())
				.addService(new GrpcImpl())
				.executor(Executors.newFixedThreadPool(10))
				.build().start();
	}

	private void startSnapshotRoutine() {
		new Timer().schedule(new FileRoutineThread(this.logDirectory), 0, 25000);
	}

	private void firstServer() {
		try {
			Properties properties = FileUtils.getConfigProperties();

			int fim = Integer.parseInt(properties.getProperty("chord.range"));
			Chord.getChodNode().setNewKey();

			Chord.getChodNode().clearRange();
			Chord.getChodNode().setRange(Chord.getChodNode().getKey(), fim+1);
			Chord.getChodNode().setRange(0, Chord.getChodNode().getKey());
			Chord.getFt().setKey(Chord.getChodNode().getKey());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void entryChord() {
		Chord.getChodNode().setNewKey();
		Chord.getFt().setKey(Chord.getChodNode().getKey());

		GrpcCommunication.findNode(this.chordIp, this.chordPort);
	}
}
