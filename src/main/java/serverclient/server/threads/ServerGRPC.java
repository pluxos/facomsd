package serverclient.server.threads;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import serverclient.server.services.impl.MessageServiceProtImpl;

import java.util.logging.Logger;

public class ServerGRPC {

    private final Logger LOGGER = Logger.getLogger(ServerGRPC.class.getName());

    private volatile boolean exit = false;

    private Server server;

    private int port;

    public ServerGRPC(int port) {
        this.port = port;
    }

    private void start() throws Exception {
        LOGGER.info("Starting the grpc server of port : " + this.port);

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

//    @Override
//    public void run() {
//        MemoryDB.getInstance();
//        this.startServer();
//    }
}
