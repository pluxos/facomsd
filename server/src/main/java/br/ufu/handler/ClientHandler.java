package br.ufu.handler;


import br.ufu.model.Command;
import br.ufu.service.QueueService;
import br.ufu.communication.GreeterGrpc;
import br.ufu.communication.Request;
import br.ufu.communication.Response;
import br.ufu.util.CommandUtil;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;

public class ClientHandler extends GreeterGrpc.GreeterImplBase {

    private static final Logger log = LogManager.getLogger(ClientHandler.class);

    private final QueueService queueService;
    private final BigInteger maxKey;
    private final Integer port;

    public ClientHandler(QueueService queueService, BigInteger maxKey, int port) {
        this.queueService = queueService;
        this.maxKey = maxKey;
        this.port = port;
    }

    private boolean checkViability(String command) {
        BigInteger key = new BigInteger(CommandUtil.getKey(command));
        return key.compareTo(maxKey) < 1 && key.compareTo(new BigInteger("0")) >= 0;
    }

    @Override
    public void message(Request req, StreamObserver<Response> responseObserver) {
        try {
            System.out.println("Received message '" + req.getReq() + "' on server " + port );
            if (checkViability(req.getReq())) {
                queueService.produceF1(new Command(req.getReq(), responseObserver));
            } else {
                String response = "Key not supported. Accepted range: [ 0 , " + maxKey.toString() + " ]";
                sendResponse(response, responseObserver);
            }
        } catch (Exception ex) {
            log.error("Server interrupted: {}", ex);
        }
    }

    public void sendResponse(String response, StreamObserver<Response> responseObserver) {
        Response resp = Response.newBuilder().setResp(response).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}

