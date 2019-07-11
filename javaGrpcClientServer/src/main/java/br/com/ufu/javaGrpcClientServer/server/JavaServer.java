package br.com.ufu.javaGrpcClientServer.server;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import br.com.ufu.javaGrpcClientServer.resources.Configuration;
import br.com.ufu.javaGrpcClientServer.resources.DataBaseRecovery;
import br.com.ufu.javaGrpcClientServer.resources.Input;
import br.com.ufu.javaGrpcClientServer.services.CrudServiceImpl;
import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.core.profile.ConsensusProfile;
import io.atomix.utils.net.Address;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class JavaServer {
	private static Properties connection;
	private static int port;
	private static File logFolder;	
	
	private Thread organizerThread;
	private Thread executionThread;
	private Thread logThread;
	private Server receptionThread;
	
	private BlockingQueue<Input> receptionQueue;	
	private BlockingQueue<Input> executionQueue;
	private BlockingQueue<Input> logQueue;
	private BlockingQueue<Input> repassQueue;
	
	private HashMap<Long, byte[]> dataBase;
	
	private int logNumber;
	private int snapshotNumber;
	
	private static int id;
    private static List<Address> addresses;
    
    private static AtomixBuilder builder;
    private static Atomix atomix;
	
	public JavaServer(String[] args) throws IOException {	
		id = Integer.parseInt(args[0]);
        addresses = new LinkedList<>();

        for(int i=1; i < args.length; i+=2) {
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
            addresses.add(address);
        }

        builder = Atomix.builder();
        
        atomix = builder
        		.withMemberId("member-" + id)
                .withAddress(addresses.get(id))
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
                .withProfiles(
                		ConsensusProfile
                		.builder()
                		.withDataPath("/tmp/member-" + id)
                		.withMembers("member-1", "member-2", "member-3")
                		.build())
                .build();

        atomix.start().join();
        System.out.println("Cluster joined");
        
		connection = Configuration.getProperties();
		port = Integer.parseInt(connection.getProperty("properties.server.port")) + id;		
		logFolder = new File(connection.getProperty("properties.server.logFolder"));
		
		if (!logFolder.exists()) {
			logFolder.mkdir();
		}
		
		receptionQueue = new LinkedBlockingQueue<Input>();	
		executionQueue = new LinkedBlockingQueue<Input>();
		logQueue = new LinkedBlockingQueue<Input>();
		repassQueue = new LinkedBlockingQueue<Input>();
		
		logNumber = DataBaseRecovery.getFileNumber(logFolder.toString(), "log");
		snapshotNumber = DataBaseRecovery.getFileNumber(logFolder.toString(), "snap");
		
		if ((snapshotNumber - logNumber) > 1) {
			logNumber = this.snapshotNumber-1;
		} else if ((snapshotNumber - logNumber) < 1) {
			snapshotNumber = this.logNumber+1;
		}
		
		try {
			dataBase = DataBaseRecovery.dataBaseRecovery(atomix, executionQueue, logFolder.toString(), logNumber, snapshotNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		organizerThread = new Thread(
				new OrganizerThread(receptionQueue, executionQueue, logQueue, repassQueue));	
		executionThread = new Thread(
				new ExecutionThread(executionQueue, dataBase));
		logThread = new Thread (
				new LogThread(logQueue, dataBase, logFolder.toString(), logNumber, snapshotNumber));
	}

	private void start() throws IOException {		
		executionThread.setDaemon(true);
		executionThread.start();
		
		while (executionQueue.size() != 0) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		organizerThread.setDaemon(true);
		organizerThread.start();
		
		logThread.setDaemon(true);
		logThread.start();
		
		receptionThread = ServerBuilder.forPort(port).addService(
				new CrudServiceImpl(receptionQueue)).build().start();
		
		System.out.println("Servidor ouvindo a porta " + port);
		System.out.println("Pressione Ctrl+C para encerrar");
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.err.println("O servidor gRPC está sendo encerrado");
				JavaServer.this.stop();
				System.err.println("O servidor foi encerrado");
			}
		});
	}

	private void stop() {
		if (receptionThread != null) {
			receptionThread.shutdown();
		}
		
		while(receptionQueue.size() != 0 
				|| executionQueue.size() != 0 
				|| logQueue.size() != 0 
				|| repassQueue.size() != 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	private void blockUntilShutdown() throws InterruptedException {
		if (receptionThread != null) {
			receptionThread.awaitTermination();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {	
		final JavaServer server = new JavaServer(args);
		server.start();
		server.blockUntilShutdown();
		
		atomix.getEventService().send("shutdown", Integer.toString(port));
	}
		
}