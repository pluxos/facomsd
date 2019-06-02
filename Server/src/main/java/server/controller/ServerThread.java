package server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.client.CommunicationManager;
import server.commons.exceptions.ServerException;
import server.commons.utils.FileUtils;
import server.commons.utils.JsonUtils;
import server.model.hashmap.Manipulator;
import server.receptor.ConsumerF1;
import server.receptor.ThreadCommand;
import server.receptor.ThreadLog;
import server.receptor.routine.FileRoutine;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Executors;

public class ServerThread implements Runnable {

	private Chord myNode;
	private Server server;
	private String chordIp = null;
	private int chordPort;

	public ServerThread(String[] args) {
		myNode = new Chord();
		myNode.setPort(Integer.parseInt(args[0]));
		if(args.length == 3) {
			this.chordIp = args[1];
			this.chordPort = Integer.parseInt(args[2]);
		}
	}

	private void stop() {
		if (this.server != null) {
			this.server.shutdown();
		}
	}

	@Override
	public void run() {
//		try {
//			Thread t = new Thread(new RecoverLog());
//			t.start();
//			t.join();
//		} catch (InterruptedException e) {
//			System.err.println("Erro ao recuperrar LOG");
//		}

		try {
			this.server = ServerBuilder.forPort(myNode.getPort())
					.addService(new GrpcImpl(myNode))
					.executor(Executors.newFixedThreadPool(10))
					.build().start();

			System.out.println("Server started, listening on " + myNode.getPort());

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				ServerThread.this.stop();
				System.err.println("*** server shut down");
			}));

			if(this.chordIp != null)
				entryChord();
			else
				firstServer();

//			startSnapshotRoutine();
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
		new Timer().schedule(new FileRoutine(), 0, 25000);
	}

	private void firstServer() {
		try {
			Properties properties = FileUtils.getConfigProperties();

			int fim = Integer.parseInt(properties.getProperty("chord.range"));
			int val = new Random().nextInt(fim);
			myNode.setKey(val);

			myNode.setRange(val, fim+1);
			myNode.setRange(0, val);
			System.out.println("KEY: " + val);
			System.out.println(myNode.getRange());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void entryChord() {
		try {
			Properties properties = FileUtils.getConfigProperties();
			int fim = Integer.parseInt(properties.getProperty("chord.range"));

			int val = new Random().nextInt(fim);

			myNode.setKey(val);

			System.out.println("KEY: " + val);

			findNode(this.chordIp, this.chordPort);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void findNode(String ip, int port){
		GreeterGrpc.GreeterStub output = CommunicationManager.initCommunication(ip, port);
		StreamObserver<FindResponse> observer = new ObserverResponse();

		output.findNode(FindMessage.newBuilder().setKey(myNode.getKey()).build(), observer);
	}

	private void getRange(FindResponse findResponse) {
		GreeterGrpc.GreeterStub output = CommunicationManager.initCommunication(findResponse.getIp(), findResponse.getPort());

		output.getRange(GetRangeRequest.newBuilder().setNode(myNode.getKey()).build(), new StreamObserver<GetRangeResponse>() {
			@Override
			public void onNext(GetRangeResponse getRangeResponse) {
				try {
					/* Recover Data */
					TypeReference<HashMap<BigInteger, byte[]>> dbRef = new TypeReference<HashMap<BigInteger, byte[]>>() {};
					HashMap<BigInteger, byte[]> map = JsonUtils.deserialize(getRangeResponse.getData(), dbRef);
					for (Map.Entry<BigInteger, byte[]> entry : map.entrySet()) {
						Manipulator.addValue(entry.getKey(), entry.getValue());
					}

					/* Set Range */
					TypeReference<ArrayList<Integer>> arrayRef = new TypeReference<ArrayList<Integer>>() {};
					myNode.setRangeWithArray(JsonUtils.deserialize(getRangeResponse.getRange(), arrayRef));

					/* Update Tabela de rotas */
				} catch (ServerException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable throwable) {

			}

			@Override
			public void onCompleted() {

			}
		});
	}

	class ObserverResponse implements StreamObserver<FindResponse> {
		@Override
		public void onNext(FindResponse findResponse) {
			if(!findResponse.getResponse()) {
				findNode(findResponse.getIp(), findResponse.getPort());
			} else {
				System.out.println("Encontrado");
				getRange(findResponse);
			}
		}

		@Override
		public void onError(Throwable throwable) {
			System.err.println(throwable.getMessage());
		}

		@Override
		public void onCompleted() {
			System.out.println("Completed");
		}
	}
}
