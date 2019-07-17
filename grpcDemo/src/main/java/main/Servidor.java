package main;

import java.io.IOException;
import crud.CrudService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Servidor {
	
	private Server server;

	  int port;

	  Servidor(int port){
	    this.port = port;

	  }
	  
	  private void start() throws IOException {
		    /* The port on which the server should run */
		    server = ServerBuilder.forPort(port)
		        .addService(new CrudService())
		        .build()
		        .start();
		    System.out.println("Server started, listening on " + port);
		    Runtime.getRuntime().addShutdownHook(new Thread() {
		      @Override
		      public void run() {
		        // Use stderr here since the logger may have been reset by its JVM shutdown hook.
		        System.err.println("*** shutting down gRPC server since JVM is shutting down");
		        Servidor.this.stop();
		        System.err.println("*** server shut down");
		      }
		    });
		  }
	  
	  private void stop() {
		    if (server != null) {
		      server.shutdown();
		    }
		  }

	  /**
	   * Await termination on the main thread since the grpc library uses daemon threads.
	   */
	  private void blockUntilShutdown() throws InterruptedException {
		  if (server != null) {
		      server.awaitTermination();
		  }
	  }
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		int port = Integer.parseInt(args[0]);
	    final Servidor server = new Servidor(port);
	    server.start();
	    server.blockUntilShutdown();
	}

}
