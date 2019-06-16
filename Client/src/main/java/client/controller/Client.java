package client.controller;

import client.business.ClientCommands;
import client.commons.exceptions.ErrorMap;
import io.grpc.GreeterGrpc;
import io.grpc.ManagedChannel;

public class Client implements Runnable {

    private final String[] args;
    private final GreeterGrpc.GreeterStub stub;

    public Client(String[] arguments, ManagedChannel channel) {
        args = arguments;
        this.stub = GreeterGrpc.newStub(channel);
    }

    @Override
    public void run() {
        boolean isTest = false;
        String testFile = null;
        try {
            isTest = !args[2].isEmpty();
            testFile = args[2];
        } catch (IndexOutOfBoundsException e) {
            isTest = false;
        }
        ClientCommands clientCommands = new ClientCommands(this.stub, isTest, testFile);
        Thread threadCommands = new Thread(clientCommands);

        threadCommands.start();

        try {
            threadCommands.join();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println(ErrorMap.UNEXPECTED_ERROR);
        }
    }
}