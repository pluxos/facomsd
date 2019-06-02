package client.controller;

import client.business.ClientCommands;
import client.commons.exceptions.ErrorMap;
import io.grpc.GreeterGrpc;
import io.grpc.ManagedChannel;

import java.util.logging.Logger;

public class Client implements Runnable {

    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final String[] args;
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public Client(String[] arguments, ManagedChannel channel) {
        args = arguments;
        this.channel = channel;
        this.blockingStub = GreeterGrpc.newBlockingStub(this.channel);
    }

    @Override
    public void run() {
        boolean isTest = false;
        String testFile = null;
        try {
            isTest = args[0].equals("teste");
            testFile = args[1];
        } catch (IndexOutOfBoundsException e) {
            isTest = false;
        }
        ClientCommands clientCommands = new ClientCommands(this.blockingStub, isTest, testFile);
        Thread threadCommands = new Thread(clientCommands);

//        ServerResponse serverResponse = new ServerResponse(input);
//        Thread threadResponse = new Thread(serverResponse);

        threadCommands.start();
//        threadResponse.start();

        try {
            threadCommands.join();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println(ErrorMap.UNEXPECTED_ERROR);
        }

//        try {
//			client.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    }
}