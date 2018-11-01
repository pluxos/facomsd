package br.ufu.connection;

import br.ufu.handler.ClientHandler;
import br.ufu.service.QueueService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServerConnection {

//    private static final Logger log = LogManager.getLogger(ServerConnection.class);
    private Server server;


    public ServerConnection(QueueService queueService, int port) {
        System.out.println("Port -> " + port);
        this.server = ServerBuilder.forPort(port)
                .addService(new ClientHandler(queueService))
                .build();
    }

    public void start() throws IOException {
        server.start();
    }

    public void blockUntilShutdown() throws InterruptedException {
        server.awaitTermination();
    }
}
