package br.ufu;

import br.ufu.client.SocketClient;
import br.ufu.handler.ClientCommandHandler;
import br.ufu.util.UserParameters;

import java.io.IOException;
import java.util.Scanner;

import static br.ufu.util.Constants.PROPERTY_SERVER_HOST;
import static br.ufu.util.Constants.PROPERTY_SERVER_PORT;

public class Client {

    private SocketClient socketClient;
    private Scanner scanner;
    private ClientCommandHandler clientCommandHandler;
    private UserParameters userParameters;

    public Client(String[] args) {
        userParameters = new UserParameters(args);
        socketClient = new SocketClient();
        scanner = new Scanner(System.in);
        clientCommandHandler = new ClientCommandHandler(getScanner(), getSocketClient());
    }

    public SocketClient getSocketClient() {
        return socketClient;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public ClientCommandHandler getClientCommandHandler() {
        return clientCommandHandler;
    }

    public UserParameters getUserParameters() {
        return userParameters;
    }

    public void start() throws IOException, InterruptedException {
        getSocketClient().startConnection(getUserParameters().get(PROPERTY_SERVER_HOST),
                getUserParameters().getInt(PROPERTY_SERVER_PORT));
        Thread t = new Thread(getClientCommandHandler());
        t.start();
        t.join();
    }

}
