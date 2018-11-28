package org.kim.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.kim.grpc.DataRequest;
import org.kim.grpc.KeyRequest;
import org.kim.grpc.ServerResponse;
import org.kim.grpc.ServiceGrpc;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int MAX = 1000000;
    private static final int MIN = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner( System.in );
        int menuOption;
        long key;
        String value;
        Random random = new Random();

        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        ServiceGrpc.ServiceBlockingStub serviceBlockingStub = ServiceGrpc.newBlockingStub(managedChannel);

        ServerResponse serverResponse;

        do {
            menuOption = showMenuOptions(scanner);

            System.out.println("--------------------");

            if (menuOption == MenuOptions.CREATE.getValue()) {
                System.out.println("Value: ");
                value = scanner.next();

                do {
                    key = random.nextInt((MAX - MIN) + 1) + MIN;

                    serverResponse = serviceBlockingStub.create(DataRequest.newBuilder()
                            .setKey(2)
                            .setData(value)
                            .build());
                } while (serverResponse.equals("Key already exists!"));

                System.out.println("Response received from server: " + serverResponse);
            }

            if (menuOption == MenuOptions.READ.getValue()) {
                System.out.println("Key: ");
                key = scanner.nextLong();

                serverResponse = serviceBlockingStub.read(KeyRequest.newBuilder()
                        .setKey(key)
                        .build());

                System.out.println("Response received from server: " + serverResponse);
            }

            if (menuOption == MenuOptions.UPDATE.getValue()) {
                System.out.println("Key: ");
                key = scanner.nextLong();

                System.out.println("Value: ");
                value = scanner.next();

                serverResponse = serviceBlockingStub.update(DataRequest.newBuilder()
                        .setKey(key)
                        .setData(value)
                        .build());

                System.out.println("Response received from server: " + serverResponse);
            }

            if (menuOption == MenuOptions.DELETE.getValue()) {
                System.out.println("Key: ");
                key = scanner.nextLong();

                serverResponse = serviceBlockingStub.delete(KeyRequest.newBuilder()
                        .setKey(key)
                        .build());

                System.out.println("Response received from server: " + serverResponse);
            }

            else System.out.println("Invalid operation");

        } while (menuOption != 10);

        managedChannel.shutdown();
    }

    private static int showMenuOptions(Scanner scanner) {
        int option;
        do {
            MenuOptions[] menuOptions = MenuOptions.values();

            for (MenuOptions menuOptions1: menuOptions) System.out.printf("[%d] - %s%n", menuOptions1.ordinal(), menuOptions1.name());

            System.out.println("Option: ");
            option = scanner.nextInt();
        } while (option > 3 || option < 0);

        return option;
    }
}
