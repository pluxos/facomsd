package br.ufu.connections;

import br.ufu.service.QueueService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ServerConnect {

    private static final Logger log = LogManager.getLogger(ServerConnect.class);
    private Server server;


    public ServerConnect(QueueService queueService, int port) {
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
