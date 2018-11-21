package org.kim.grpc.server.model;

import io.grpc.stub.StreamObserver;
import org.kim.grpc.OperationRequest;
import org.kim.grpc.OperationResponse;

public class ArrivingGrpc {

    private StreamObserver<OperationResponse> responseGrpc;
    private OperationRequest requestGrpc;

    public OperationRequest getRequestGrpc() { return requestGrpc; }

    public void setRequestGrpc(OperationRequest requestGrpc) { this.requestGrpc = requestGrpc; }

    public StreamObserver<OperationResponse> getResponseGrpc() { return responseGrpc; }

    public void setResponseGrpc(StreamObserver<OperationResponse> responseGrpc) { this.responseGrpc = responseGrpc; }

    public ArrivingGrpc(OperationRequest requestGrpc, StreamObserver<OperationResponse> responseGrpc) {
        this.responseGrpc = responseGrpc;
        this.requestGrpc = requestGrpc;
    }
}
