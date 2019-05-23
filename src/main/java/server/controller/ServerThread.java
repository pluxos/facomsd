package server.controller;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.commons.Rows.RowF1;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.utils.DataCodificator;
import server.receptor.*;

import java.io.*;
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

			this.server = ServerBuilder.forPort(12345)
					.addService(new ServerThread.GreeterImpl())
					.executor(Executors.newFixedThreadPool(10))
					.build()
					.start();

			logger.info("Server started, listening on " + 12345);

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				ServerThread.this.stop();
				System.err.println("*** server shut down");
			}));

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

		public void createUser(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
			System.out.println("CREATE User");

            GenericCommand genericCommand = new GenericCommand();
            genericCommand.setOutput(responseObserver);
            genericCommand.setCode(DataCodificator.stringToBigInteger(request.getId()));
            genericCommand.setData(DataCodificator.stringToByteArray(request.getData()));
            genericCommand.setMethod(Method.CREATE.toString());

            RowF1.addItem(genericCommand);
		}

		public void getUser(GetRequest request, StreamObserver<GetResponse> responseObserver) {
			System.out.println("GET User");

			GenericCommand genericCommand = new GenericCommand();
			genericCommand.setOutput(responseObserver);
			genericCommand.setCode(DataCodificator.stringToBigInteger(request.getId()));
			genericCommand.setMethod(Method.GET.toString());

			RowF1.addItem(genericCommand);
		}

		public void updateUser(UpdateRequest request, StreamObserver<UpdateResponse> responseObserver) {
			System.out.println("UPDATE User");

            GenericCommand genericCommand = new GenericCommand();
            genericCommand.setOutput(responseObserver);
            genericCommand.setCode(DataCodificator.stringToBigInteger(request.getId()));
            genericCommand.setData(DataCodificator.stringToByteArray(request.getData()));
            genericCommand.setMethod(Method.UPDATE.toString());

            RowF1.addItem(genericCommand);
		}

		public void deleteUser(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
			System.out.println("DELETE User");

            GenericCommand genericCommand = new GenericCommand();
            genericCommand.setOutput(responseObserver);
            genericCommand.setCode(DataCodificator.stringToBigInteger(request.getId()));
            genericCommand.setMethod(Method.DELETE.toString());

            RowF1.addItem(genericCommand);
		}
	}
}
