package com.sd.projeto1.main;

import com.sd.projeto1.proto.SubscribeResponse;
import com.sd.projeto1.util.PropertyManagement;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerThreadGRPC implements Runnable {

	private Queue<String> logQueue;

	private Queue<String> executeQueue;

	private Operacoes context;
	
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	private PropertyManagement settings;
	
	private List< String > subscribeAlert = new ArrayList< String >();
	
	private Map< String, List<StreamObserver< SubscribeResponse >> > observers;


	public ServerThreadGRPC(Queue< String > logQueue, Queue< String > executeQueue, PropertyManagement settings, Map< String, List<StreamObserver< SubscribeResponse >> > observers ) {
		super();
		this.logQueue = logQueue;
		this.executeQueue = executeQueue;
		this.settings = settings;
		this.observers = observers;
	}

	@Override
	public void run() {

		try {

			Server server = ServerBuilder.forPort( settings.getPortGRPC()).addService( new com.sd.projeto1.service.ContextService( logQueue, executeQueue, observers ) ).build();

			// Start the server
			server.start();

			// Server threads are running in the background.
			System.out.println( "Server started" );
			// Don't exit the main thread. Wait until server is terminated.
			
			Future< String > future = wait( server );
			try {
				while( !future.isDone() ) {
					Thread.sleep( 300 );
				}
				future.get();
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			} catch ( ExecutionException e ) {
				e.printStackTrace();
			} finally {
				future.cancel( true );
			}
			
			server.awaitTermination();

		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	public Future< String > wait( Server server ) {
		return executor.submit( () -> {
			server.awaitTermination();
			return null;
		} );
	}

}
