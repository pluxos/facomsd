package br.ufu.handler;


import br.ufu.communication.RequestKeyValue;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import br.ufu.communication.GreeterGrpc;
import br.ufu.communication.Request;
import br.ufu.communication.Response;
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
        BigInteger key = new BigInteger(command);
        return key.compareTo(maxKey) < 1 && key.compareTo(new BigInteger("0")) >= 0;
    }

    @Override
    public void create(RequestKeyValue req, StreamObserver<Response> responseObserver) {
        try {
            String operation = "CREATE " + req.getKey() + " " + req.getValue();
            Command command = new Command(operation, responseObserver);
            printMessage("CREATE", operation);
            if (checkViability(req.getKey())) {
                queueService.produceF1(command);
            } else {
                sendResponse(responseObserver);
            }
        } catch (Exception ex) {
            log.error("Server interrupted: {}", ex);
        }
    }

    @Override
    public void read(Request req, StreamObserver<Response> responseObserver) {
        try {
            String operation = "READ " + req.getKey();
            Command command = new Command(operation, responseObserver);
            printMessage("READ", operation);
            if (checkViability(req.getKey())) {
                queueService.produceF1(command);
            } else {
                sendResponse(responseObserver);
            }
        } catch (Exception ex) {
            log.error("Server interrupted: {}", ex);
        }
    }

    @Override
    public void update(RequestKeyValue req, StreamObserver<Response> responseObserver) {
        try {
            String operation = "UPDATE " + req.getKey() + " " + req.getValue();
            Command command = new Command(operation, responseObserver);
            printMessage("UPDATE", operation);
            if (checkViability(req.getKey())) {
                queueService.produceF1(command);
            } else {
                sendResponse(responseObserver);
            }
        } catch (Exception ex) {
            log.error("Server interrupted: {}", ex);
        }
    }
    @Override
    public void delete(Request req, StreamObserver<Response> responseObserver) {
        try {
            String operation = "DELETE " + req.getKey();
            Command command = new Command(operation, responseObserver);
            printMessage("DELETE", operation);
            if (checkViability(req.getKey())) {
                queueService.produceF1(command);
            } else {
                sendResponse(responseObserver);
            }
        } catch (Exception ex) {
            log.error("Server interrupted: {}", ex);
        }
    }

    private void sendResponse(StreamObserver<Response> responseObserver) {
        String response = "Key not supported. Accepted range: [ 0 , " + maxKey.toString() + " ]";
        Response resp = Response.newBuilder().setResp(response).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    private void printMessage(String operation, String command) {
        System.out.println("Received "+ operation +" command '"+ command +"' on server "+ port );
    }
}

