package br.com.ufu.javaGrpcClientServer.client;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

import br.com.ufu.javaGrpcClientServer.CrudServiceGrpc;
import br.com.ufu.javaGrpcClientServer.resources.Configuration;
import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.utils.net.Address;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class JavaClient {
	private static Properties connection;
	private static String serverAddress;
	private static int port, defaultPort;
	
	private static Thread menuThread;
	private static Thread responseThread;
	
	private ManagedChannel channel;
	private CrudServiceGrpc.CrudServiceBlockingStub stub;
	private BlockingQueue<Object> responseQueue = new LinkedBlockingQueue<Object>();
	
	private int id;
	private List<Address> addresses;
	
	public JavaClient(String[] args) throws IOException {     
		id = Integer.parseInt(args[0]);
        addresses = new LinkedList<>();

        for(int i=1; i < args.length; i+=2)
        {
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
            addresses.add(address);
        }

        AtomixBuilder builder = Atomix.builder();
        Atomix atomix = builder
        		.withMemberId("client-" + id)
                .withAddress(new Address("127.0.0.1", 6000 + id))
                .withMembershipProvider(
                		BootstrapDiscoveryProvider
                		.builder()
                        .withNodes( 
                        		Node.builder()
                                        .withId("member-0")
                                        .withAddress(addresses.get(0))
                                        .build(),
                                Node.builder()
                                        .withId("member-1")
                                        .withAddress(addresses.get(1))
                                        .build(),
                                Node.builder()
                                        .withId("member-2")
                                        .withAddress(addresses.get(2))
                                        .build())
                        .build())
                .build();

        atomix.start().join();
        System.out.println("Cluster joined");
        
		connection = Configuration.getProperties();
		serverAddress = connection.getProperty("properties.server.host");
		defaultPort = Integer.parseInt(connection.getProperty("properties.server.port"));
		
		if(atomix.getMembershipService().getMembers().iterator().hasNext()) {
			System.out.println(atomix.getMembershipService().getMembers().iterator().next().id().id());
			port = defaultPort + Integer.parseInt(atomix.getMembershipService().getMembers().iterator().next().id().id().replace("member-", ""));
		}
		
		channel = ManagedChannelBuilder
	    		.forAddress(serverAddress, port).usePlaintext().build();
		stub = CrudServiceGrpc.newBlockingStub(channel);
		
		atomix.getEventService().subscribe("shutdown", message -> {
			System.out.println("Mudando de servidor!");
			
				if(atomix.getMembershipService().getMembers().iterator().hasNext()) {
					port = defaultPort + Integer.parseInt(atomix.getMembershipService().getMembers().iterator().next().id().id().replace("member-", ""));
					
					channel = ManagedChannelBuilder
				    		.forAddress(serverAddress, port).usePlaintext().build();
					stub = CrudServiceGrpc.newBlockingStub(channel);
			}
			return CompletableFuture.completedFuture(message);
			});
		
		/*atomix.getBroadcastService().addListener("teste", message -> {
			System.out.println("Mudando de servidor!");
			
			if(atomix.getMembershipService().getMembers().iterator().hasNext()) {
				port = defaultPort + Integer.parseInt(atomix.getMembershipService().getMembers().iterator().next().id().id().replace("member-", ""));
				
				if(channel != null) {
					channel.shutdown();
				}
				
				channel = ManagedChannelBuilder
			    		.forAddress(serverAddress, port).usePlaintext().build();
				stub = CrudServiceGrpc.newBlockingStub(channel);
			}
		});*/
		
		menuThread = new Thread(new MenuThread(stub, responseQueue));
		menuThread.setDaemon(true);
		
		responseThread = new Thread(new ResponseThread(responseQueue));
		responseThread.setDaemon(true);
	}
	
	private void start() throws InterruptedException {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {	
				JavaClient.this.stop();							
			}
		});		
		
		menuThread.start();
		responseThread.start();
	}

	@SuppressWarnings("deprecation")
	private void stop() {
		System.err.println("O cliente está sendo encerrado");
		
		if (responseThread.isAlive()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("Ocorreu uma falha!\n"
						+ "Não foi possível aguardar novas repostas do servidor.");
				e.printStackTrace();
			} finally {
				responseThread.stop();
			}
		}
				
		if (channel != null) {
			channel.shutdown();
		}
		
		System.err.println("O cliente foi encerrado");	
	}
	
	private void blockUntilShutdown() throws InterruptedException {
		menuThread.join();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		final JavaClient client = new JavaClient(args);
		client.start();
		client.blockUntilShutdown();
		client.stop();
	}		
}
