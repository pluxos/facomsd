package br.ufu;

import br.ufu.client.ClientConnection;
import br.ufu.handler.ClientCommandHandler;
import br.ufu.util.UserParameters;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import static br.ufu.util.Constants.PROPERTY_SERVER_HOST;
import static br.ufu.util.Constants.PROPERTY_SERVER_PORT;

public class Client {

    private Scanner scanner;
    private ClientCommandHandler clientCommandHandler;
    private UserParameters userParameters;
    private ClientConnection clientConnection;

    public Client(String[] args) {
        userParameters = new UserParameters(args);
        scanner = new Scanner(System.in);
    }


    public Scanner getScanner() {
        return scanner;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public ClientCommandHandler getClientCommandHandler() {
        return clientCommandHandler;
    }

    public UserParameters getUserParameters() {
        return userParameters;
    }

    public void start() throws IOException, InterruptedException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = socket.getLocalAddress();
        Integer port = socket.getLocalPort();
        String id = ip.toString() + port.toString();

        clientConnection = new ClientConnection(getUserParameters().get(PROPERTY_SERVER_HOST),
                getUserParameters().getInt(PROPERTY_SERVER_PORT));
        this.clientCommandHandler = new ClientCommandHandler(getScanner(), getClientConnection(), id);

        Thread t = new Thread(getClientCommandHandler());
        t.start();
        t.join();
        clientConnection.shutdown();
    }

}
