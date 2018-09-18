package br.ufu;

import br.ufu.client.SocketClient;
import br.ufu.handler.ClientCommandHandler;

import java.io.IOException;
import java.util.Scanner;

import static br.ufu.util.Constants.PROPERTY_SERVER_HOST;
import static br.ufu.util.Constants.PROPERTY_SERVER_PORT;
import static br.ufu.util.UserParameters.get;
import static br.ufu.util.UserParameters.getInt;

public class Client {

    private final SocketClient socketClient;
    private final Scanner scanner;
    private final ClientCommandHandler clientCommandHandler;

    public Client() {
        socketClient = new SocketClient();
        scanner = new Scanner(System.in);
        clientCommandHandler = new ClientCommandHandler(scanner, socketClient);
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

    public void start() throws IOException, InterruptedException {
        socketClient.startConnection(get(PROPERTY_SERVER_HOST), getInt(PROPERTY_SERVER_PORT));
        Thread t = new Thread(clientCommandHandler);
        t.start();
        t.join();
    }

}
