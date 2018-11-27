package org.kim.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = ServerBuilder.forPort(8080)
                    .addService(new ServiceImpl())
                    .build();

            System.out.println("Starting server...");
            server.start();
            System.out.println("Server started!");
            server.awaitTermination();
        }
        catch (IOException | InterruptedException e) { System.out.println("Server error: " + e.getMessage()); }
    }
}
