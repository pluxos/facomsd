package client.controller;

import client.business.ClientCommands;
import client.business.ServerResponse;
import client.commons.exceptions.ErrorMap;
import client.commons.utils.SocketConnection;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private final String[] args;

    public Client(String[] arguments) {
        args = arguments;
    }

    @Override
    public void run() {
        Socket client = null;
        PrintStream output = null;
        Scanner input = null;
        try {
            client = SocketConnection.getSocket();
            output = new PrintStream(client.getOutputStream());
            input = new Scanner(client.getInputStream());
        } catch (IOException e) {
            System.err.println(ErrorMap.CONNECTION_ERROR.getMessage());
            return;
        }
        boolean isTest = false;
        String testFile = null;
        try {
            isTest = args[0].equals("teste");
            testFile = args[1];
        } catch (IndexOutOfBoundsException e) {
            isTest = false;
        }
        ClientCommands clientCommands = new ClientCommands(output, isTest, testFile);
        Thread threadCommands = new Thread(clientCommands);

        ServerResponse serverResponse = new ServerResponse(input);
        Thread threadResponse = new Thread(serverResponse);

        threadCommands.start();
        threadResponse.start();

        try {
            threadCommands.join();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println(ErrorMap.UNEXPECTED_ERROR);
        }
        
        try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}