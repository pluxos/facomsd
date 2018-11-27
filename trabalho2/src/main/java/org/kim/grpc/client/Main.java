package org.kim.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.kim.grpc.DataRequest;
import org.kim.grpc.ServerResponse;
import org.kim.grpc.ServiceGrpc;

public class Main {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        ServiceGrpc.ServiceBlockingStub serviceBlockingStub = ServiceGrpc.newBlockingStub(managedChannel);

        ServerResponse serverResponse = serviceBlockingStub.create(DataRequest.newBuilder()
                .setKey(1)
                .setData("10")
                .build());

        System.out.println("Response received from server: " + serverResponse);

        managedChannel.shutdown();
    }
}
