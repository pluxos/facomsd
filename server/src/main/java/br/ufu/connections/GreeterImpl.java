package br.ufu.connections;


import io.grpc.examples.servergreeting.GreeterGrpc;
import io.grpc.examples.servergreeting.Request;
import io.grpc.examples.servergreeting.RequestM;
import io.grpc.examples.servergreeting.Response;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    private static final Logger log = LogManager.getLogger(GreeterImpl.class);

//    private BlockingQueue<String> filatemp;
//    private BlockingQueue<String> filaResposta;
//    private Map<String, StreamObserver<Response>> monitorargrpc; //Identificador para Observer
//    private Map<BigInteger, ArrayList<String>> monitorarChaveId; //Chave para Lista de Identificadores
//
//    public GreeterImpl(BlockingQueue um, BlockingQueue resposta,
//                       Map<String, StreamObserver<Response>> monitorar,
//                       Map<BigInteger, ArrayList<String>> monitorar2) {
//
//        filatemp = um;
//        filaResposta = resposta;
//        monitorargrpc = monitorar;
//        monitorarChaveId = monitorar2;
//    }

    @Override
    public void say(Request req, StreamObserver<Response> responseObserver) {
        try {
//            filatemp.add(req.getAll());
//            String resposta;
//            resposta = filaResposta.take();

            System.out.println("Msg recebida: " + req.getAll());

            Response response = Response.newBuilder().setResp(req.getAll()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception ex) {
            log.error("Server interrupted: {}", ex);
        }
//        catch (InterruptedException ex) {
    }

    @Override
    public void monitor(RequestM req, StreamObserver<Response> responseObserver) {

//        BigInteger chave = new BigInteger(req.getKey());
//        String individual = req.getClient();
//
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

