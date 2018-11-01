package br.ufu.handler;


import br.ufu.model.Command;
import br.ufu.service.QueueService;
import br.ufu.communication.GreeterGrpc;
import br.ufu.communication.Request;
import br.ufu.communication.Response;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientHandler extends GreeterGrpc.GreeterImplBase {

    private static final Logger log = LogManager.getLogger(ClientHandler.class);

    private final QueueService queueService;

    public ClientHandler(QueueService queueService) {
        this.queueService = queueService;
    }

    @Override
    public void message(Request req, StreamObserver<Response> responseObserver) {
        try {
            System.out.println("Msg recebida: " + req.getReq());
            queueService.produceF1(new Command(req.getReq(), responseObserver));
        } catch (Exception ex) {
            log.error("Server interrupted: {}", ex);
        }
    }
}

