package serverclient.server.threads;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import serverclient.model.Message;
import serverclient.model.MessageOld;
import serverclient.server.services.impl.MessageServiceProtImpl;
import serverclient.server.threads.handlers.MessageData;
import serverclient.server.threads.messagequeues.secondstage.SecondThirdQueueThread;
import serverclient.server.threads.messagequeues.thirdstage.DatabaseProcessingThread;
import serverclient.server.threads.messagequeues.thirdstage.LogThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class ServerGRPC {

    private final Logger LOGGER = Logger.getLogger(ServerGRPC.class.getName());

    private volatile boolean exit = false;

    private Server server;

    private int port;

    private static volatile BlockingQueue<MessageData> fila1;
    private static volatile BlockingQueue<Message> fila2;
    private static volatile BlockingQueue<MessageData> fila3;
    private static volatile BlockingQueue<Message> fila4;

    private ExecutorService queueThreadPool = Executors.newFixedThreadPool(4);

    static {
        fila1 = new LinkedBlockingDeque<>();
        fila2 = new LinkedBlockingDeque<>();
        fila3 = new LinkedBlockingDeque<>();
        fila4 = new LinkedBlockingDeque<>();
    }

    public ServerGRPC(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        LOGGER.info("Starting the grpc server of port : " + this.port);

        this.queueThreadPool.submit(new SecondThirdQueueThread());
        this.queueThreadPool.submit(new DatabaseProcessingThread());
        this.queueThreadPool.submit(new LogThread());

        server = ServerBuilder.forPort(port)
                .addService(new MessageServiceProtImpl())
                .build()
                .start();

        LOGGER.info("Server started. Listening on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** JVM is shutting down. Turning off grpc server as well ***");
            this.stop();
            System.err.println("*** shutdown complete ***");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static BlockingQueue<MessageData> getFila1() {
        return fila1;
    }

    public static BlockingQueue<Message> getFila2() {
        return fila2;
    }

    public static BlockingQueue<MessageData> getFila3() {
        return fila3;
    }

//    @Override
//    public void run() {
//        MemoryDB.getInstance();
//        this.startServer();
//    }
}
