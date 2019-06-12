package server.receptor;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import server.business.consumers.CommandExecutorThread;
import server.business.consumers.LogPersistentThread;
import server.business.consumers.OrchestratorThread;
import server.business.consumers.ServerConnectorThread;
import server.business.persistence.recovery.LogRecovererThread;
import server.business.persistence.routine.Counter;
import server.business.persistence.routine.FileRoutineThread;
import server.commons.chord.Chord;
import server.commons.chord.FingerTable;
import server.commons.chord.Node;
import server.commons.utils.FileUtils;
import server.requester.GrpcCommunication;

import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.Executors;

public class ServerThread implements Runnable {

	private String logDirectory;
	private Server server;
	private String chordIp = null;
	private int chordPort;

	public ServerThread(String[] args) {
		Chord.setNode(new Node());
		Chord.setFt(new FingerTable());

		this.logDirectory = args[0];
		Chord.getNode().setIp(args[1]);
		Chord.getNode().setPort(Integer.parseInt(args[2]));
		if(args.length == 5) {
			this.chordIp = args[3];
			this.chordPort = Integer.parseInt(args[4]);
			GrpcCommunication.ip = args[3];
			GrpcCommunication.port = this.chordPort;
		}
		Counter.startCounter(args[0]);
	}

	private void stop() {
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
			this.server = ServerBuilder.forPort(Chord.getNode().getPort())
					.addService(new GrpcImpl())
					.executor(Executors.newFixedThreadPool(10))
					.build().start();

			System.out.println("Server started, listening on " + Chord.getNode().getPort());

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				ServerThread.this.stop();
				System.err.println("*** server shut down");
			}));

			if(this.chordIp != null)
				entryChord();
			else
				firstServer();

			startSnapshotRoutine();
			Thread tConsumer = new Thread(new OrchestratorThread());
			Thread tCommand = new Thread(new CommandExecutorThread());
			Thread tLog = new Thread(new LogPersistentThread());
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

	private void startSnapshotRoutine() {
		new Timer().schedule(new FileRoutineThread(this.logDirectory), 0, 25000);
	}

	private void firstServer() {
		try {
			Properties properties = FileUtils.getConfigProperties();

			int fim = Integer.parseInt(properties.getProperty("chord.range"));
			Chord.getNode().setNewKey();

			Chord.getNode().setRange(Chord.getNode().getKey(), fim+1);
			Chord.getNode().setRange(0, Chord.getNode().getKey());
			Chord.getFt().setKey(Chord.getNode().getKey());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void entryChord() {
		Chord.getNode().setNewKey();
		Chord.getFt().setKey(Chord.getNode().getKey());

		GrpcCommunication.findNode(this.chordIp, this.chordPort);
	}
}
