package server.receptor;

import com.fasterxml.jackson.core.type.TypeReference;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.business.persistence.Manipulator;
import server.commons.chord.ChodNode;
import server.commons.chord.Chord;
import server.commons.chord.ChordUtils;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.rows.RowF1;
import server.commons.utils.JsonUtils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class GrpcImpl extends GreeterGrpc.GreeterImplBase {

    GrpcImpl(){}

    public void create(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(BigInteger.valueOf(request.getCode()));
        genericCommand.setData(request.getData());
        genericCommand.setMethod(Method.CREATE.toString());

        RowF1.addItem(genericCommand);
    }

    public void get(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(BigInteger.valueOf(request.getCode()));
        genericCommand.setMethod(Method.GET.toString());

        RowF1.addItem(genericCommand);
    }

    public void update(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(BigInteger.valueOf(request.getCode()));
        genericCommand.setData(request.getData());
        genericCommand.setMethod(Method.UPDATE.toString());

        RowF1.addItem(genericCommand);
    }

    public void delete(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(BigInteger.valueOf(request.getCode()));
        genericCommand.setMethod(Method.DELETE.toString());

        RowF1.addItem(genericCommand);
    }

    @Override
    public void findNode(FindMessage request, StreamObserver<FindResponse> responseObserver) {
        if( Chord.getChodNode().getRange().contains(request.getKey()) ) {
            responseObserver.onNext(
                    FindResponse.newBuilder()
                            .setResponse(true)
                            .setPort(Chord.getChodNode().getPort())
                            .build()
            );

            responseObserver.onCompleted();
        } else {
            ChodNode chodNodeResponsible = Chord.getFt().catchResponsibleNode(request.getKey());

            if(chodNodeResponsible.getIp() == null)
                chodNodeResponsible.setIp("localhost");

            responseObserver.onNext(
                    FindResponse.newBuilder()
                            .setResponse(false)
                            .setIp(chodNodeResponsible.getIp())
                            .setPort(chodNodeResponsible.getPort())
                            .build()
            );
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getRange(GetRangeRequest request, StreamObserver<GetRangeResponse> responseObserver) {
        ChodNode newChodNode = null;
        try {
            newChodNode = JsonUtils.deserialize(request.getNode(), ChodNode.class);
        } catch (ServerException e) {
            e.printStackTrace();
        }

        if(newChodNode.getKey() != Chord.getChodNode().getKey()) {
            newChodNode.setRangeWithArray(Chord.getChodNode().updateRange(Chord.getChodNode().getKey(), newChodNode.getKey()));
            ChordUtils.notifyNewNode(newChodNode);

            HashMap<BigInteger, byte[]> dbRecovery = Manipulator.removeValues(Chord.getChodNode().getRange());

            try {
                responseObserver.onNext(
                        GetRangeResponse.newBuilder()
                                .setNode(JsonUtils.serialize(Chord.getChodNode()))
                                .setFingerT(JsonUtils.serialize(Chord.getFt()))
                                .setData(JsonUtils.serialize(dbRecovery))
                                .setRange(JsonUtils.serialize(newChodNode.getRange()))
                                .build()
                );
            } catch (ServerException e) {
                e.printStackTrace();
            }
            responseObserver.onCompleted();

            Chord.getFt().updateFT(newChodNode);
        } else {
            try {
                responseObserver.onNext(
                        GetRangeResponse
                                .newBuilder()
                                .setNode(JsonUtils.serialize(Chord.getChodNode()))
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
            TypeReference<Map<Integer, ChodNode>> typeRef = new TypeReference<Map<Integer, ChodNode>>() {};
            Map<Integer, ChodNode> ft = JsonUtils.deserialize(request.getFingerT(), typeRef);

            if(!request.getNode().equals("")) {
                ChodNode newChodNode = JsonUtils.deserialize(request.getNode(), ChodNode.class);
                Chord.getFt().updateFT(newChodNode);
            }


            if(Chord.getFt().updateFT(ft) > -1) {
                ChordUtils.notifyUpdateFT();
            }

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
            ChodNode chodNode = JsonUtils.deserialize(request.getNode(), ChodNode.class);
            ChodNode newChodNode = JsonUtils.deserialize(request.getNewNode(), ChodNode.class);

            int flagNode = Chord.getFt().updateFT(chodNode);
            int flagNewNode = Chord.getFt().updateFT(newChodNode);

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

    @Override
    public void safeOutput(SafeOutputRequest request, StreamObserver<SafeOutputResponse> responseObserver) {
        try {
            TypeReference<Map<BigInteger, byte[]>> typeRef = new TypeReference<Map<BigInteger, byte[]>>() {};
            Map<BigInteger, byte[]> db = JsonUtils.deserialize(request.getData(), typeRef);

            ChodNode chodNode = JsonUtils.deserialize(request.getNode(), ChodNode.class);

            Chord.getChodNode()
                    .getRange()
                    .addAll(chodNode.getRange());

            Manipulator.addValues(db);

            Chord.getFt().removeNode(chodNode);

            ChordUtils.notifyUpdateFT();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
}
