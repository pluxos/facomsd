package br.com.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.configuration.Configuration;
import br.com.configuration.SocketSetting;
import br.com.thread.ClientSenderGrpcThread;

public class ClientGrpc {

	private static SocketSetting grpccServerSettings;
	private static ExecutorService executor;

	
	public static void main( String[] args ) {
		
		try {
			
			grpccServerSettings = Configuration.grpcServerSettings(args[0]);
			
			executor = Executors.newFixedThreadPool(50);
			ClientSenderGrpcThread grpcClientThread = new ClientSenderGrpcThread( grpccServerSettings );
			executor.execute( grpcClientThread );
			
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		
	}
	
}
