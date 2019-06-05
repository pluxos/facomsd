package server.controller;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.client.CommunicationManager;
import server.client.GetRangeObserver;
import server.commons.Chord.Chord;
import server.commons.Chord.Node;
import server.commons.Chord.FingerTable;
import server.commons.exceptions.ServerException;
import server.commons.utils.FileUtils;
import server.commons.utils.JsonUtils;
import server.receptor.ConsumerF1;
import server.receptor.RecoverLog;
import server.receptor.ThreadCommand;
import server.receptor.ThreadLog;
import server.receptor.routine.Counter;
import server.receptor.routine.FileRoutine;

import java.io.IOException;
import java.util.*;
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
		Chord.getNode().setPort(Integer.parseInt(args[1]));
		if(args.length == 4) {
			this.chordIp = args[2];
			this.chordPort = Integer.parseInt(args[3]);
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
			Thread t = new Thread(new RecoverLog(this.logDirectory));
			t.start();
			t.join();
		} catch (InterruptedException e) {
			System.err.println("Erro ao recuperrar LOG");
		}

		try {
			this.server = ServerBuilder.forPort(Chord.getNode().getPort())
					.addService(new GrpcImpl(Chord.getNode(), Chord.getFt()))
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
			Thread tConsumer = new Thread(new ConsumerF1());
			Thread tCommand = new Thread(new ThreadCommand());
			Thread tLog = new Thread(new ThreadLog());
			tConsumer.start();
			tCommand.start();
			tLog.start();

			this.server.awaitTermination();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void startSnapshotRoutine() {
		new Timer().schedule(new FileRoutine(this.logDirectory), 0, 25000);
	}

	private void firstServer() {
		try {
			Properties properties = FileUtils.getConfigProperties();

			int fim = Integer.parseInt(properties.getProperty("chord.range"));
			Chord.getNode().setNewKey();

			Chord.getNode().setRange(Chord.getNode().getKey(), fim+1);
			Chord.getNode().setRange(0, Chord.getNode().getKey());
			Chord.getFt().setKey(Chord.getNode().getKey());
			Chord.getFt().updateFT(Chord.getNode());

			System.out.println("KEY: " +Chord.getNode().getKey());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void entryChord() {
		Chord.getNode().setNewKey();
		Chord.getFt().setKey(Chord.getNode().getKey());
		Chord.getFt().updateFT(Chord.getNode());

		System.out.println("KEY: " + Chord.getNode().getKey());

		findNode(this.chordIp, this.chordPort);
	}

	private void findNode(String ip, int port){
		GreeterGrpc.GreeterStub output = CommunicationManager.initCommunication(ip, port);
		StreamObserver<FindResponse> observer = new ObserverResponse();

		output.findNode(FindMessage.newBuilder().setKey(Chord.getNode().getKey()).build(), observer);
	}

	private void getRange(FindResponse findResponse) throws ServerException {
		GreeterGrpc.GreeterStub output = CommunicationManager.initCommunication(findResponse.getIp(), findResponse.getPort());

		output.getRange(
				GetRangeRequest.newBuilder().setNode(JsonUtils.serialize(Chord.getNode())).build(),
				new GetRangeObserver());
	}

	class ObserverResponse implements StreamObserver<FindResponse> {
		@Override
		public void onNext(FindResponse findResponse) {
			if(!findResponse.getResponse()) {
				findNode(findResponse.getIp(), findResponse.getPort());
			} else {
				try {
					System.out.println(findResponse.getPort());
					getRange(findResponse);
				} catch (ServerException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onError(Throwable throwable) {
			System.err.println(throwable.getMessage());
		}

		@Override
		public void onCompleted() {
		}
	}
}
