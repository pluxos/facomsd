package br.com.ufu.ClientServer.server;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.core.map.DistributedMap;
import io.atomix.core.profile.ConsensusProfile;
import io.atomix.utils.net.Address;

public class Server {
    private static int port;
    private static Config config = new Config();
    private static BlockingQueue<Input> queue1 = new LinkedBlockingQueue<Input>();
    private static BlockingQueue<Input> queue2 = new LinkedBlockingQueue<Input>();
    private static BlockingQueue<Input> queue3 = new LinkedBlockingQueue<Input>();
    
    private static int id;
    private static List<Address> addresses;
    
    private static AtomixBuilder builder;

    private static void initialization() {
        Log log = new Log();
        log.open();
        log.read(queue3);
        log.close();
    }

    public static void main(String args[]) {
    	id = Integer.parseInt(args[0]);
        addresses = new LinkedList<>();

        for(int i=1; i < args.length; i+=2) {
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
            addresses.add(address);
        }

        builder = Atomix.builder();
        
        Atomix atomixServer = builder
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
        
        Atomix atomixClient = builder
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

        atomixServer.start().join();
        atomixClient.start().join();

        System.out.println("Cluster joined");
        
        config.load();

        if (config.getIsLoaded()) {
            port = config.getPort();

            try {
                initialization();
                
                DistributedMap<BigInteger, byte[]> dataBase = atomixClient.<BigInteger, byte[]>mapBuilder("database")
                        .withCacheEnabled()
                        .build();

                Thread execution = new Thread(new ExecutionThread(dataBase, queue3));
                execution.setDaemon(true);
                execution.start();

                while (!queue3.isEmpty())
                    execution.join(1,0);

                Thread organizer = new Thread(new OrganizerThread(queue1, queue2, queue3));
                organizer.setDaemon(true);
                organizer.start();

                Thread log = new Thread(new LogThread(queue2));
                log.setDaemon(true);
                log.start();

                ServerSocket welcomeSocket = new ServerSocket(port + id);
                System.out.println("Servidor ouvindo a porta " + (port + id));


                while (true) {
                    try {
                        Socket connectionSocket = welcomeSocket.accept();
                        System.out.println("Cliente conectado: " + connectionSocket.getInetAddress().getHostAddress() + ":" + connectionSocket.getPort());

                        ObjectInputStream inputStream = new ObjectInputStream(connectionSocket.getInputStream());
                        ObjectOutputStream outputStream = new ObjectOutputStream(connectionSocket.getOutputStream());

                        Thread reception = new Thread(new ReceptionThread(connectionSocket, inputStream, outputStream, queue1));
                        reception.setDaemon(true);
                        reception.start();

                        outputStream.flush();
                        outputStream.writeObject("Conexão estabelecida!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
