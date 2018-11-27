package org.kim.grpc.server;

import io.grpc.stub.StreamObserver;
import org.kim.grpc.DataRequest;
import org.kim.grpc.KeyRequest;
import org.kim.grpc.ServerResponse;
import org.kim.grpc.ServiceGrpc;

import java.util.HashMap;
import java.util.Map;

public class ServiceImpl extends ServiceGrpc.ServiceImplBase {

    private static Map<Long, String> data = new HashMap<>();

    @org.jetbrains.annotations.Contract(pure = true)
    private synchronized static boolean checkIfExists(Long key) { return data.containsKey(key); }

    @Override
    public void create(DataRequest request, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Request CREATE received from client");

        data.put(request.getKey(), request.getData());

        ServerResponse response = ServerResponse.newBuilder()
                .setResponse("Data saved!")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void read(KeyRequest request, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Request READ received from client");

        ServerResponse response;

        if (checkIfExists(request.getKey())) {
            response = ServerResponse.newBuilder()
                    .setResponse(data.get(request.getKey()))
                    .build();
        }

        else {
            response = ServerResponse.newBuilder()
                    .setResponse("Key not found!")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(DataRequest request, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Request UPDATE received from client");

        ServerResponse response;

        if (checkIfExists(request.getKey())) {
            response = ServerResponse.newBuilder()
                    .setResponse(data.put(request.getKey(), request.getData()))
                    .build();
        }

        else {
            response = ServerResponse.newBuilder()
                    .setResponse("Key not found!")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(KeyRequest request, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Request DELETE received from client");

        ServerResponse response;

        if (checkIfExists(request.getKey())) {
            data.remove(request.getKey());
            response = ServerResponse.newBuilder()
                    .setResponse("Data deleted!")
                    .build();
        }

        else {
            response = ServerResponse.newBuilder()
                    .setResponse("Key not found!")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
