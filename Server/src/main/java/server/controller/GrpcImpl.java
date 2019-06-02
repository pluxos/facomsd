package server.controller;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.commons.Rows.RowF1;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.utils.DataCodificator;

public class GrpcImpl extends GreeterGrpc.GreeterImplBase {
    GrpcImpl(){}

    public void createUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(DataCodificator.stringToBigInteger(request.getCode()));
        genericCommand.setData(DataCodificator.stringToByteArray(request.getData()));
        genericCommand.setMethod(Method.CREATE.toString());

        RowF1.addItem(genericCommand);
    }

    public void getUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(DataCodificator.stringToBigInteger(request.getCode()));
        genericCommand.setMethod(Method.GET.toString());

        RowF1.addItem(genericCommand);
    }

    public void updateUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(DataCodificator.stringToBigInteger(request.getCode()));
        genericCommand.setData(DataCodificator.stringToByteArray(request.getData()));
        genericCommand.setMethod(Method.UPDATE.toString());

        RowF1.addItem(genericCommand);
    }

    public void deleteUser(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {
        GenericCommand genericCommand = new GenericCommand();
        genericCommand.setOutput(responseObserver);
        genericCommand.setCode(DataCodificator.stringToBigInteger(request.getCode()));
        genericCommand.setMethod(Method.DELETE.toString());

        RowF1.addItem(genericCommand);
    }

    @Override
    public void findNode(FindMessage request, StreamObserver<FindResponse> responseObserver) {
        if( Chord.getRange().contains(request.getKey()) ) {
            responseObserver.onNext(FindResponse.newBuilder().setResponse(true).setPort(Chord.getPort()).build());
            responseObserver.onCompleted();
        } else {
            /* Procurar na FT quem poderia ser responsável por essa key */
            responseObserver.onNext(FindResponse.newBuilder().setResponse(false).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getRange(GetRangeRequest request, StreamObserver<GetRangeResponse> responseObserver) {

    }
}
