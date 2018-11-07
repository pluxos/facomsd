package br.ufu.connection;

import br.ufu.handler.ClientHandler;
import br.ufu.service.QueueService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.math.BigInteger;

public class ServerConnection {

    private Server server;


    public ServerConnection(QueueService queueService, int port, BigInteger maxKey) {
        this.server = ServerBuilder.forPort(port)
                .addService(new ClientHandler(queueService, maxKey, port))
                .build();
    }

    public void start() throws IOException {
        server.start();
    }

    public void shutdown() throws InterruptedException {
        server.awaitTermination();
    }
}
