package server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.client.CommunicationManager;
import server.commons.Chord.Chord;
import server.commons.Chord.FingerTable;
import server.commons.exceptions.ServerException;
import server.commons.utils.FileUtils;
import server.commons.utils.JsonUtils;
import server.model.hashmap.Manipulator;
import server.receptor.ConsumerF1;
import server.receptor.RecoverLog;
import server.receptor.ThreadCommand;
import server.receptor.ThreadLog;
import server.receptor.routine.FileRoutine;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Executors;

public class ServerThread implements Runnable {

	private Chord myNode;
	private FingerTable ft;
	private String logDirectory;
	private Server server;
	private String chordIp = null;
	private int chordPort;

	public ServerThread(String[] args) {
		this.myNode = new Chord();
		this.ft = new FingerTable();
		this.logDirectory = args[0];
		this.myNode.setPort(Integer.parseInt(args[1]));
		if(args.length == 4) {
			this.chordIp = args[2];
			this.chordPort = Integer.parseInt(args[3]);
		}
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
			this.server = ServerBuilder.forPort(myNode.getPort())
					.addService(new GrpcImpl(myNode, ft))
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
			myNode.setNewKey();

			myNode.setRange(myNode.getKey(), fim+1);
			myNode.setRange(0, myNode.getKey());
			ft.setKey(myNode.getKey());
			ft.updateFT(myNode);

			System.out.println("KEY: " +myNode.getKey());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void entryChord() {
		myNode.setNewKey();
		ft.setKey(myNode.getKey());
		this.ft.updateFT(myNode);

		System.out.println("KEY: " + myNode.getKey());

		findNode(this.chordIp, this.chordPort);
	}

	private void findNode(String ip, int port){
		GreeterGrpc.GreeterStub output = CommunicationManager.initCommunication(ip, port);
		StreamObserver<FindResponse> observer = new ObserverResponse();

		output.findNode(FindMessage.newBuilder().setKey(myNode.getKey()).build(), observer);
	}

	private void getRange(FindResponse findResponse) throws ServerException {
		GreeterGrpc.GreeterStub output = CommunicationManager.initCommunication(findResponse.getIp(), findResponse.getPort());

		output.getRange(
				GetRangeRequest.newBuilder().setNode(JsonUtils.serialize(this.myNode)).build(),
				new StreamObserver<GetRangeResponse>() {
					@Override
					public void onNext(GetRangeResponse getRangeResponse) {
						try {
							Chord newNode = JsonUtils.deserialize(getRangeResponse.getNode(), Chord.class);
							if(newNode.getKey() != myNode.getKey()) {
								/* Recover Data */
								TypeReference<HashMap<BigInteger, byte[]>> dbRef;
								dbRef = new TypeReference<HashMap<BigInteger, byte[]>>() {};

								HashMap<BigInteger, byte[]> map = JsonUtils.deserialize(getRangeResponse.getData(), dbRef);
								for (Map.Entry<BigInteger, byte[]> entry : map.entrySet()) {
									Manipulator.addValue(entry.getKey(), entry.getValue());
								}

								/* Set Range */
								TypeReference<ArrayList<Integer>> arrayRef = new TypeReference<ArrayList<Integer>>() {
								};
								myNode.setRangeWithArray(JsonUtils.deserialize(getRangeResponse.getRange(), arrayRef));
								ft.updateFT(myNode);

								/* Update Tabela de rotas */
								System.err.println("ATUALIZANDO TABELA DE ROTAS");
								ft.updateFT(newNode);
							} else {
								myNode.setNewKey();
								ft.setKey(myNode.getKey());
								System.out.println("KEY: " + myNode.getKey());
								findNode(chordIp, chordPort);
							}
						} catch (ServerException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Throwable throwable) {
						System.out.println(throwable.fillInStackTrace().getMessage());
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
