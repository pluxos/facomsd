package server.receptor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.business.persistence.Manipulator;
import server.commons.chord.Chord;
import server.commons.chord.ChordUtils;
import server.commons.chord.Node;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.rows.RowF1;
import server.commons.utils.JsonUtils;

public class GrpcImpl extends GreeterGrpc.GreeterImplBase {

    GrpcImpl(){}

    public void createUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(BigInteger.valueOf(request.getCode()));
        genericCommand.setData(request.getData());
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
        genericCommand.setData(request.getData());
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

        if( Chord.getNode().getRange().contains(request.getKey()) ) {
            responseObserver.onNext(
                    FindResponse.newBuilder()
                            .setResponse(true)
                            .setPort(Chord.getNode().getPort())
                            .build()
            );

            responseObserver.onCompleted();
        } else {
            /* Procurar na FT quem poderia ser responsável por essa key */
            Node nodeResponsible = Chord.getFt().catchResponsibleNode(request.getKey());

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


        if(newNode.getKey() != Chord.getNode().getKey()) {
            /* Defino o Range de dados que preciso retornar, dado o nó vindo do request */
            /* Redefinir o meu range */
            newNode.setRangeWithArray(Chord.getNode().updateRange(Chord.getNode().getKey(), newNode.getKey()));
            ChordUtils.notifyNewNode(newNode);

            /* Pego todos os dados correspondente a este range que está na HT */

            HashMap<BigInteger, byte[]> dbRecovery = Manipulator.removeValues(Chord.getNode().getRange());

            /* Enviar resposta com o meu nó! o range que estou enviando, e os dados */
            try {
                responseObserver.onNext(
                        GetRangeResponse.newBuilder()
                                .setNode(JsonUtils.serialize(Chord.getNode()))
                                .setFingerT(JsonUtils.serialize(Chord.getFt()))
                                .setData(JsonUtils.serialize(dbRecovery))
                                .setRange(JsonUtils.serialize(newNode.getRange()))
                                .build()
                );
            } catch (ServerException e) {
                e.printStackTrace();
            }
            responseObserver.onCompleted();

            /* Update Tabela de rota */
            Chord.getFt().updateFT(newNode);

            ChordUtils.notifyUpdateFT();
        } else {
            /* Mesma Chave! reportar erro! */
            try {
                responseObserver.onNext(
                        GetRangeResponse
                                .newBuilder()
                                .setNode(JsonUtils.serialize(Chord.getNode()))
                                .build()
                );
            } catch (ServerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateFT(UpdateFTRequest request, StreamObserver<UpdateFTResponse> responseObserver) {
        try {
            TypeReference<Map<Integer, Node>> typeRef = new TypeReference<Map<Integer, Node>>() {};
            Map<Integer, Node> ft = JsonUtils.deserialize(request.getFingerT(), typeRef);

            Chord.getFt().updateFT(ft);

            responseObserver.onNext(
                    UpdateFTResponse.newBuilder()
                            .setUpdate(true)
                            .setFingerT(JsonUtils.serialize(Chord.getFt()))
                            .build());

            responseObserver.onCompleted();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newNode(NewNodeRequest request, StreamObserver<NewNodeResponse> responseObserver) {
        try {
            Node node = JsonUtils.deserialize(request.getNode(), Node.class);
            Node newNode = JsonUtils.deserialize(request.getNewNode(), Node.class);
            System.out.println("New Node " +newNode.getKey());

            int flagNode = Chord.getFt().updateFT(node);
            int flagNewNode = Chord.getFt().updateFT(newNode);

            if(flagNode != -1 || flagNewNode != -1) {
                ChordUtils.notifyUpdateFT();
                responseObserver.onNext(
                        NewNodeResponse
                                .newBuilder()
                                .setUpdate(true)
                                .setFingerT(JsonUtils.serialize(Chord.getFt()))
                                .build()
                );
            } else {
                responseObserver.onNext(
                        NewNodeResponse
                                .newBuilder()
                                .setUpdate(false)
                                .build()
                );
            }

            responseObserver.onCompleted();
        } catch (ServerException e) {
            e.printStackTrace();
        }

    }
}
