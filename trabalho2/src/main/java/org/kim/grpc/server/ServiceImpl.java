package org.kim.grpc.server;

import io.grpc.stub.StreamObserver;
import org.kim.grpc.DataRequest;
import org.kim.grpc.KeyRequest;
import org.kim.grpc.ServerResponse;
import org.kim.grpc.ServiceGrpc;

import java.util.concurrent.BlockingQueue;

public class ServiceImpl extends ServiceGrpc.ServiceImplBase {

    private BlockingQueue<Command> F1;

    public ServiceImpl(BlockingQueue<Command> f1) {
        F1 = f1;
    }

    @Override
    public void create(DataRequest request, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Request CREATE received from client");

        Command command = new Command("CREATE", request.getKey(), request.getData(), responseObserver);

        F1.add(command);


        /*System.out.println("Request CREATE received from client");

        ServerResponse response;

        if (data.containsKey(request.getKey())) {
            response = ServerResponse.newBuilder()
                    .setResponse("Key already exists!")
                    .build();
        }
        else {
            data.put(request.getKey(), request.getData());

            response = ServerResponse.newBuilder()
                    .setResponse("DataStorage saved! Key: " + request.getKey())
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();*/
    }

    @Override
    public void read(KeyRequest request, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Request READ received from client");

        Command command = new Command("READ", request.getKey(), responseObserver);

        F1.add(command);


        /*System.out.println("Request READ received from client");

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
        responseObserver.onCompleted();*/
    }

    @Override
    public void update(DataRequest request, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Request UPDATE received from client");

        Command command = new Command("UPDATE", request.getKey(), request.getData(), responseObserver);

        F1.add(command);


        /*System.out.println("Request UPDATE received from client");

        ServerResponse response;

        if (checkIfExists(request.getKey())) {
            data.put(request.getKey(), request.getData());

            response = ServerResponse.newBuilder()
                    .setResponse("DataStorage updated!")
                    .build();
        }

        else {
            response = ServerResponse.newBuilder()
                    .setResponse("Key not found!")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();*/
    }

    @Override
    public void delete(KeyRequest request, StreamObserver<ServerResponse> responseObserver) {
        System.out.println("Request DELETE received from client");

        Command command = new Command("DELETE", request.getKey(), responseObserver);

        F1.add(command);


        /*System.out.println("Request DELETE received from client");

        ServerResponse response;

        if (checkIfExists(request.getKey())) {
            data.remove(request.getKey());
            response = ServerResponse.newBuilder()
                    .setResponse("DataStorage deleted!")
                    .build();
        }

        else {
            response = ServerResponse.newBuilder()
                    .setResponse("Key not found!")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();*/
    }
}
