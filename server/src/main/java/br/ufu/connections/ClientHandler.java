package br.ufu.connections;


import br.ufu.model.Command;
import br.ufu.service.QueueService;
import io.grpc.examples.servergreeting.GreeterGrpc;
import io.grpc.examples.servergreeting.Request;
import io.grpc.examples.servergreeting.RequestM;
import io.grpc.examples.servergreeting.Response;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;

public class ClientHandler extends GreeterGrpc.GreeterImplBase {

    private static final Logger log = LogManager.getLogger(ClientHandler.class);

    private final QueueService queueService;

    public ClientHandler(QueueService queueService) {
        this.queueService = queueService;
    }

    @Override
    public void say(Request req, StreamObserver<Response> responseObserver) {
        try {
            System.out.println("Msg recebida: " + req.getAll());
            queueService.produceF1(new Command(req.getAll(), responseObserver));
//            String resposta =  queueService.consumeF1();

        } catch (Exception ex) {
            log.error("Server interrupted: {}", ex);
        }
//    } catch (InterruptedException ex) {
    }

    @Override
    public void monitor(RequestM req, StreamObserver<Response> responseObserver) {

        BigInteger chave = new BigInteger(req.getKey());
        String individual = req.getClient();

//        monitorarChaveId.computeIfAbsent(chave, k -> new ArrayList<String>()).add(individual);

        Response response = Response.newBuilder().setResp("Chave sendo monitorada").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void notify(Request req, StreamObserver<Response> responseObserver) {
//        monitorargrpc.put(req.getAll(), responseObserver);
        //responseObserver.onCompleted();
    }

}

