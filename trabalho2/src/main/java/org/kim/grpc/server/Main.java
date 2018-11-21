package org.kim.grpc.server;

public class Main {

    public static void main(String[] args) {
        Thread threadMainGrpc = new ThreadServerGrpc();
        threadMainGrpc.start();
    }
}
