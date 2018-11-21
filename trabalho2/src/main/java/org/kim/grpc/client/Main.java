package org.kim.grpc.client;

import org.kim.grpc.client.model.MenuOptions;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.kim.grpc.OperationServiceGrpc;
import org.kim.grpc.OperationRequest;
import org.kim.grpc.OperationResponse;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    private final ManagedChannel channel;
    private final OperationServiceGrpc.OperationServiceStub stub;

    public Main(String host, int port){
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    public Main(ManagedChannel channel) {
        this.channel = channel;
        stub = OperationServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException { channel.shutdown().awaitTermination(5, TimeUnit.SECONDS); }

    public void makeOperation(int op, Integer key, String value) {
        BigInteger newKey;

        try{ newKey = BigInteger.valueOf(key); }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return;
        }

        final OperationRequest operation = OperationRequest.newBuilder()
                .setOperation(op)
                .setKeyLength(newKey.toByteArray().length)
                .setKey(key)
                .setValueLength(value.getBytes(StandardCharsets.UTF_16).length - 2)
                .setValue(value)
                .build();

        StreamObserver<OperationResponse> response = new StreamObserver<OperationResponse>() {
            @Override
            public void onNext(OperationResponse operationResponse) { System.out.println(operationResponse.getValue()+ " " + operationResponse.getResponse()); }

            @Override
            public void onError(Throwable throwable) { }

            @Override
            public void onCompleted() { System.out.println("Disconnected"); }
        };

        try { stub.makeOperationService(operation, response); }
        catch (StatusRuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main client = new Main("localhost", 8090);
        Scanner scanner = new Scanner(System.in);

        int menuOption = 4;
        int key;
        String value = "";

        try {
            while (true) {
                menuOption = showMenu(scanner);

                if (menuOption == 10) break;

                System.out.println("--------------------");
                System.out.println("Enter Key: ");
                key = scanner.nextInt();

                if (menuOption == MenuOptions.CREATE.getValue() || menuOption == MenuOptions.UPDATE.getValue()) {
                    System.out.println("Enter value: ");
                    value = scanner.next();
                }

                client.makeOperation(menuOption, key, value);
            }
        } finally { client.shutdown(); }
    }

    public static int showMenu(Scanner scanner){
        int option;
        do {
            MenuOptions[] menu = MenuOptions.values();
            for (MenuOptions m: menu) System.out.printf("[%d] - %s%n", m.ordinal(), m.name());

            System.out.println("Option: ");
            option = scanner.nextInt();
        }while ((option > 5 || option < 0) && option != 10);

        return option;
    }
}
