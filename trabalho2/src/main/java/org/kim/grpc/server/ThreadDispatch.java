package org.kim.grpc.server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.kim.grpc.DataRequest;
import org.kim.grpc.KeyRequest;
import org.kim.grpc.ServerResponse;
import org.kim.grpc.ServiceGrpc;

public class ThreadDispatch extends Thread {

    private static ThreadDispatch threadDispatch;

    private int port;
    private Command command;

    public ThreadDispatch(int port, Command command) {
        this.port = port;
        this.command = command;
    }

    public ThreadDispatch() {}

    public static ThreadDispatch init() {
        if (threadDispatch == null) {
            threadDispatch = new ThreadDispatch();
            threadDispatch.start();
        }

        return threadDispatch;
    }

    @Override
    public void run() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", port)
                .usePlaintext()
                .build();

        ServiceGrpc.ServiceBlockingStub serviceBlockingStub = ServiceGrpc.newBlockingStub(managedChannel);

        ServerResponse serverResponse;

        switch (command.getType()) {
            case "CREATE":
                serverResponse = serviceBlockingStub.create(DataRequest.newBuilder()
                        .setKey(command.getKey())
                        .setData(command.getData())
                        .build());

                break;

            case "READ":
                serverResponse = serviceBlockingStub.read(KeyRequest.newBuilder()
                        .setKey(command.getKey())
                        .build());

                break;

            case "UPDATE":
                serverResponse = serviceBlockingStub.update(DataRequest.newBuilder()
                        .setKey(command.getKey())
                        .setData(command.getData())
                        .build());

                break;

            case "DELETE":
                serverResponse = serviceBlockingStub.delete(KeyRequest.newBuilder()
                        .setKey(command.getKey())
                        .build());

                break;
        }
    }
}
