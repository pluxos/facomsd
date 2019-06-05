package br.ufu.ds.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public final class ServerRpc implements Runnable{

    private static final Logger logger = Logger.getLogger(ServerRpc.class.getName());

    private Server server;
    private int port;

    public ServerRpc(int port) {
        this.port = port;
        this.server = ServerBuilder
                .forPort(port)
                .addService(new ServerService())
                .build();
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void start() {
        try {
            this.server.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                ServerRpc.this.stop();
                System.err.println("*** server shut down");
            }));
            logger.info("Server start on port: " + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    @Override
    public void run() {
        start();
        try {
            blockUntilShutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
