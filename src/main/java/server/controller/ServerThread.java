package server.controller;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.*;
import io.grpc.stub.StreamObserver;
import server.commons.utils.UserControl;
import server.receptor.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerThread implements Runnable {

	private String filePath;
	private static final Logger logger = Logger.getLogger(ServerThread.class.getName());
	private Server server;

	public ServerThread(String filePath) {
		this.filePath = filePath;
	}

	private void stop() {
		if (this.server != null) {
			this.server.shutdown();
		}

	}

	@Override
	public void run() {

		/*File file = new File(filePath);
		if (file.exists()) {
			try {
				Thread t = new Thread(new RecoverLog(new BufferedReader(new FileReader(file))));
				t.start();
				t.join();
			} catch (InterruptedException e) {
				System.err.println("Erro ao recuperrar LOG");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}*/

		try {
			this.server = ServerBuilder.forPort(12345).addService(new ServerThread.GreeterImpl()).build().start();
			logger.info("Server started, listening on " + 12345);
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					System.err.println("*** shutting down gRPC server since JVM is shutting down");
					ServerThread.this.stop();
					System.err.println("*** server shut down");
				}
			});

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

	static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
		GreeterImpl() {
		}

		public void createUser(CreateRequest req, StreamObserver<CreateResponse> responseObserver) {
			System.out.println("CREATE User");
			CreateResponse reply = CreateResponse.newBuilder().setStatus("Sucesso").build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		public void getUser(GetRequest request, StreamObserver<GetResponse> responseObserver) {
			System.out.println("GET User");
			GetResponse reply = GetResponse.newBuilder().setEmail("Sucesso").build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		public void updateUser(UpdateRequest request, StreamObserver<UpdateResponse> responseObserver) {
			System.out.println("UPDATE User");
			UpdateResponse reply = UpdateResponse.newBuilder().setMessage("Sucesso").build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		public void deleteUser(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
			System.out.println("DELETE User");
			DeleteResponse reply = DeleteResponse.newBuilder().setMessage("Sucesso").build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
	}
}
