package server.controller;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.commons.Chord.Node;
import server.commons.Chord.FingerTable;
import server.commons.Rows.RowF1;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.utils.DataCodificator;
import server.commons.utils.JsonUtils;
import server.model.hashmap.Manipulator;

import java.math.BigInteger;
import java.util.HashMap;

public class GrpcImpl extends GreeterGrpc.GreeterImplBase {
    private Node node;
    private FingerTable ft;

    GrpcImpl(Node node, FingerTable ft){
        this.node = node;
        this.ft = ft;
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
        /* Verificar se a key é igual a minha, para que ele troque */

        if( this.node.getRange().contains(request.getKey()) ) {
            responseObserver.onNext(FindResponse.newBuilder().setResponse(true).setPort(this.node.getPort()).build());
            responseObserver.onCompleted();
        } else {
            /* Procurar na FT quem poderia ser responsável por essa key */
            Node nodeResponsible = this.ft.catchResponsibleNode(request.getKey());

            if(nodeResponsible.getIp() == null)
                nodeResponsible.setIp("localhost");

            responseObserver.onNext(
                    FindResponse.newBuilder()
                            .setResponse(false)
                            .setIp(nodeResponsible.getIp())
                            .setPort(nodeResponsible.getPort())
                            .build()
            );
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getRange(GetRangeRequest request, StreamObserver<GetRangeResponse> responseObserver) {
        Node newNode = null;
        try {
            newNode = JsonUtils.deserialize(request.getNode(), Node.class);
        } catch (ServerException e) {
            e.printStackTrace();
        }

        assert newNode != null;
        if(newNode.getKey() != this.node.getKey()) {
            /* Defino o Range de dados que preciso retornar, dado o nó vindo do request */
            /* Redefinir o meu range */
            Node node = new Node();
            node.setKey(newNode.getKey());
            node.setRangeWithArray(this.node.updateRange(this.node.getKey(), newNode.getKey()));

            /* Pego todos os dados correspondente a este range que está na HT */

            HashMap<BigInteger, byte[]> dbRecovery = Manipulator.removeValues(node.getRange());

            /* Enviar resposta com o meu nó! o range que estou enviando, e os dados */
            try {
                responseObserver.onNext(
                        GetRangeResponse.newBuilder()
                                .setNode(JsonUtils.serialize(this.node))
                                .setData(JsonUtils.serialize(dbRecovery))
                                .setRange(JsonUtils.serialize(node.getRange()))
                                .build()
                );
            } catch (ServerException e) {
                e.printStackTrace();
            }
            responseObserver.onCompleted();

            /* Update Tabela de rota */

            this.ft.updateFT(this.node);
            this.ft.updateFT(node);
        } else {
            /* Mesma Chave! reportar erro! */
            try {
                responseObserver.onNext(
                        GetRangeResponse
                                .newBuilder()
                                .setNode(JsonUtils.serialize(this.node))
                                .build()
                );
            } catch (ServerException e) {
                e.printStackTrace();
            }
        }
    }
}
