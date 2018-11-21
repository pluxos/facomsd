package org.kim.grpc.server;

import org.kim.grpc.server.model.ArrivingGrpc;
import org.kim.grpc.server.threads.ThreadProcessGrpc;

import io.grpc.stub.StreamObserver;

import org.kim.grpc.OperationRequest;
import org.kim.grpc.OperationResponse;
import org.kim.grpc.OperationServiceGrpc;

import static org.kim.grpc.server.helper.DataStorage.getInstance;

public class GrpcImp extends OperationServiceGrpc.OperationServiceImplBase {

    public GrpcImp() { ThreadProcessGrpc.init(); }

    @Override
    public void makeOperationService(OperationRequest request, StreamObserver<OperationResponse> responseObserver) {
        getInstance().addArrivingGrpc(new ArrivingGrpc(request, responseObserver));
    }
}
