package server.controller;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.commons.Rows.RowF1;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.utils.DataCodificator;
import server.commons.utils.JsonUtils;
import server.model.hashmap.Manipulator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GrpcImpl extends GreeterGrpc.GreeterImplBase {
    private Chord node;

    GrpcImpl(Chord node){
        this.node = node;
    }

    public void createUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(BigInteger.valueOf(request.getCode()));
        genericCommand.setData(DataCodificator.stringToByteArray(request.getData()));
        genericCommand.setMethod(Method.CREATE.toString());

        RowF1.addItem(genericCommand);
    }

    public void getUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(BigInteger.valueOf(request.getCode()));
        genericCommand.setMethod(Method.GET.toString());

        RowF1.addItem(genericCommand);
    }

    public void updateUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(BigInteger.valueOf(request.getCode()));
        genericCommand.setData(DataCodificator.stringToByteArray(request.getData()));
        genericCommand.setMethod(Method.UPDATE.toString());

        RowF1.addItem(genericCommand);
    }

    public void deleteUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(BigInteger.valueOf(request.getCode()));
        genericCommand.setMethod(Method.DELETE.toString());

        RowF1.addItem(genericCommand);
    }

    @Override
    public void findNode(FindMessage request, StreamObserver<FindResponse> responseObserver) {
        if( this.node.getRange().contains(request.getKey()) ) {
            responseObserver.onNext(FindResponse.newBuilder().setResponse(true).setPort(this.node.getPort()).build());
            responseObserver.onCompleted();
        } else {
            /* Procurar na FT quem poderia ser responsável por essa key */
            responseObserver.onNext(FindResponse.newBuilder().setResponse(false).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getRange(GetRangeRequest request, StreamObserver<GetRangeResponse> responseObserver) {

        if(request.getNode() != this.node.getKey()) {
            /* Defino o Range de dados que preciso retornar, dado o nó vindo do request */
            /* Redefinir o meu range */
            ArrayList<Integer> rangeRes = this.node.updateRange(this.node.getKey(), request.getNode());

            /* Pego todos os dados correspondente a este range que está na HT */

            HashMap<BigInteger, byte[]> dbRecovery = Manipulator.removeValues(rangeRes);

            /* Enviar resposta com o meu nó! o range que estou enviando, e os dados */
            try {
                responseObserver.onNext(
                        GetRangeResponse.newBuilder()
                                .setNode(this.node.getKey())
                                .setData(JsonUtils.serialize(dbRecovery))
                                .setRange(JsonUtils.serialize(rangeRes))
                                .build()
                );
            } catch (ServerException e) {
                e.printStackTrace();
            }
            responseObserver.onCompleted();

        } else {
            /* Mesma Chave! reportar erro! */
        }
    }
}
