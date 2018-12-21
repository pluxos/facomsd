package br.com.server;

import br.com.atomix.Atomix;
import br.com.configuration.Configuration;
import br.com.configuration.SocketSetting;
import br.com.context.Context;
import br.com.proto.ContextProto.SubscribeResponse;
import br.com.thread.*;
import io.atomix.catalyst.transport.Address;
import io.atomix.copycat.client.CopycatClient;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomServer {
    private static final Logger LOG = LoggerFactory.getLogger(CustomServer.class);

    private static ServerSocket serverSocket;
    private static SocketSetting mySettings;
    private static SocketSetting grpcServerSettings;
    private static Queue<String> logQueue = new LinkedList<String>();
    private static Queue<String> queue = new LinkedList<String>();
    private static Map<String, List<StreamObserver<SubscribeResponse>>> observers = new HashMap<String, List<StreamObserver<SubscribeResponse>>>();
    private static Context context;
    private static ExecutorService executor;
    private static Boolean semaphore;
    private static CopycatClient copycatClient;


    public static void main(String args[]) throws Exception {

        /**
         * Externalizacoes das configuracoes
         */
        context = new Context();
        context.load(Paths.get("src/main/resources/snapshot/snapshot.txt"), Paths.get("src/main/resources/log/log.txt"));
        mySettings = Configuration.serverSettings(args[0]);
        grpcServerSettings = Configuration.grpcServerSettings(args[0]);
        //serverSocket = new ServerSocket(mySettings.getPort());

        List<Address> addressList = Arrays
                .asList(
                        new Address("localhost", 8700),
                        new Address("localhost", 8701),
                        new Address("localhost", 8702));

        executor = Executors.newFixedThreadPool(50);
        semaphore = false;


        Atomix atomix = new Atomix();
        atomix.start(mySettings.getId(), addressList, mySettings.getPort());
        copycatClient = atomix.getCopycatClient();

        /**
         * Declaracao das Threads
         */
        LogThread logThread = new LogThread(logQueue, semaphore);

        ExecutorThread executorThread = new ExecutorThread(serverSocket, logQueue, context, observers, semaphore, copycatClient);

        ServerRecieveThread recieveThread = new ServerRecieveThread(serverSocket, queue);

        GrpcThread grpcServerThread = new GrpcThread(logQueue, queue, context, grpcServerSettings, observers);

        SnapshotThread snapshotThread = new SnapshotThread(context, semaphore);




        /**
         * Execucao das Threads
         */
        executor.execute(logThread);
        executor.execute(executorThread);
        executor.execute(recieveThread);
        executor.execute(grpcServerThread);
        executor.execute(snapshotThread);




    }

}