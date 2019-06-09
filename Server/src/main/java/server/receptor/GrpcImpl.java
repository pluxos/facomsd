package server.receptor;

import java.math.BigInteger;
import java.util.HashMap;

import io.grpc.FindMessage;
import io.grpc.FindResponse;
import io.grpc.GenericRequest;
import io.grpc.GenericResponse;
import io.grpc.GetRangeRequest;
import io.grpc.GetRangeResponse;
import io.grpc.GreeterGrpc;
import io.grpc.stub.StreamObserver;
import server.business.persistence.Manipulator;
import server.commons.chord.FingerTable;
import server.commons.chord.Node;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.rows.RowF1;
import server.commons.utils.JsonUtils;

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
            newNode.setRangeWithArray(this.node.updateRange(this.node.getKey(), newNode.getKey()));

            /* Pego todos os dados correspondente a este range que está na HT */

            HashMap<BigInteger, byte[]> dbRecovery = Manipulator.removeValues(node.getRange());

            /* Enviar resposta com o meu nó! o range que estou enviando, e os dados */
            try {
                responseObserver.onNext(
                        GetRangeResponse.newBuilder()
                                .setNode(JsonUtils.serialize(this.node))
                                .setData(JsonUtils.serialize(dbRecovery))
                                .setRange(JsonUtils.serialize(newNode.getRange()))
                                .build()
                );
            } catch (ServerException e) {
                e.printStackTrace();
            }
            responseObserver.onCompleted();

            /* Update Tabela de rota */

            this.ft.updateFT(this.node);
            this.ft.updateFT(newNode);
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
