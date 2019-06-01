package server.controller;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.Executors;

import io.grpc.GenericRequest;
import io.grpc.GenericResponse;
import io.grpc.GreeterGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import server.commons.Rows.RowF1;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.utils.DataCodificator;
import server.receptor.ConsumerF1;
import server.receptor.RecoverLog;
import server.receptor.ThreadCommand;
import server.receptor.ThreadLog;
import server.receptor.routine.FileRoutine;

public class ServerThread implements Runnable {

	private Server server;
	private int port;

	public ServerThread(String[] args) {
		this.port = Integer.parseInt(args[0]);
	}

	private void stop() {
		if (this.server != null) {
			this.server.shutdown();
		}
	}

	@Override
	public void run() {
		/*try {
			Thread t = new Thread(new RecoverLog());
			t.start();
			t.join();
		} catch (InterruptedException e) {
			System.err.println("Erro ao recuperrar LOG");
		}*/

		try {
			this.server = ServerBuilder.forPort(this.port)
					.addService(new ServerThread.GreeterImpl())
					.executor(Executors.newFixedThreadPool(10))
					.build().start();

			System.out.println("Server started, listening on " + this.port);

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				ServerThread.this.stop();
				System.err.println("*** server shut down");
			}));

			//startSnapshotRoutine();
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

	static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
		GreeterImpl() {
		}

		public void createUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
			GenericCommand genericCommand = new GenericCommand();
			genericCommand.setOutput(responseObserver);
			genericCommand.setCode(DataCodificator.stringToBigInteger(request.getCode()));
			genericCommand.setData(DataCodificator.stringToByteArray(request.getData()));
			genericCommand.setMethod(Method.CREATE.toString());

			RowF1.addItem(genericCommand);
		}

		public void getUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
			GenericCommand genericCommand = new GenericCommand();
			genericCommand.setOutput(responseObserver);
			genericCommand.setCode(DataCodificator.stringToBigInteger(request.getCode()));
			genericCommand.setMethod(Method.GET.toString());

			RowF1.addItem(genericCommand);
		}

		public void updateUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
			GenericCommand genericCommand = new GenericCommand();
			genericCommand.setOutput(responseObserver);
			genericCommand.setCode(DataCodificator.stringToBigInteger(request.getCode()));
			genericCommand.setData(DataCodificator.stringToByteArray(request.getData()));
			genericCommand.setMethod(Method.UPDATE.toString());

			RowF1.addItem(genericCommand);
		}

		public void deleteUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
			GenericCommand genericCommand = new GenericCommand();
			genericCommand.setOutput(responseObserver);
			genericCommand.setCode(DataCodificator.stringToBigInteger(request.getCode()));
			genericCommand.setMethod(Method.DELETE.toString());

			RowF1.addItem(genericCommand);
		}
	}
}
