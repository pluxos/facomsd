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
    private final QueueService queueService;

    public ServerConnect(QueueService queueService, int port) {
        this.queueService = queueService;
        this.server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build();
    }

    public void start() throws IOException, InterruptedException {
        log.info("gRPC server starting");
        server.start();
        log.info("gRPC server started");
        server.awaitTermination();
    }

}
