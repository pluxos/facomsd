package br.com.server;

import java.net.ServerSocket;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.configuration.Configuration;
import br.com.configuration.SocketSetting;
import br.com.context.Context;
import br.com.proto.ContextProto.SubscribeResponse;
import br.com.thread.*;
import io.grpc.stub.StreamObserver;

public class CustomServer {

    private static ServerSocket serverSocket;
    private static SocketSetting mySettings;
    private static SocketSetting grpcServerSettings;
    private static Queue<String> logQueue = new LinkedList<String>();
    private static Queue<String> Queue = new LinkedList<String>();
    private static Map<String, List<StreamObserver<SubscribeResponse>>> observers = new HashMap<String, List<StreamObserver<SubscribeResponse>>>();
    private static Context context;
    private static ExecutorService executor;
    private static Boolean semaphore;


    public static void main(String args[]) throws Exception {

        /**
         * Externalizacoes das configuracoes
         */
        context = new Context();
        context.load(Paths.get("src/main/resources/snapshot/snapshot.txt"), Paths.get("src/main/resources/log/log.txt"));
        mySettings = Configuration.serverSettings();
        grpcServerSettings = Configuration.grpcServerSettings();
        serverSocket = new ServerSocket(mySettings.getPort());

        executor = Executors.newFixedThreadPool(50);
        semaphore = false;

        /**
         * Declaracao das Threads
         */
        LogThread logThread = new LogThread(logQueue, semaphore);

        ExecutorThread executorThread = new ExecutorThread(serverSocket, logQueue, context, observers, semaphore);

        ServerRecieveThread recieveThread = new ServerRecieveThread(serverSocket, Queue);

        GrpcThread grpcServerThread = new GrpcThread(logQueue, Queue, context, grpcServerSettings, observers);

        SnapshotThread snapshotThread = new SnapshotThread(context, semaphore);

        /**
         * Execucao das Threads
         */
        SplitThread splitThread = new SplitThread();
        splitThread.start();
        executor.execute(logThread);
        executor.execute(executorThread);
        executor.execute(recieveThread);
        executor.execute(grpcServerThread);
        executor.execute(snapshotThread);

    }

}