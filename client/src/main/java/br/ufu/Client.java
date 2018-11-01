package br.ufu;

import br.ufu.client.ClientConnect;
import br.ufu.client.ResponseListener;
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
    private ClientConnect clientConnect;
    private ResponseListener responseListener;

    public Client(String[] args) {
        userParameters = new UserParameters(args);
        scanner = new Scanner(System.in);
    }


    public Scanner getScanner() {
        return scanner;
    }

    public ClientConnect getClientConnect() {
        return clientConnect;
    }

    public ClientCommandHandler getClientCommandHandler() {
        return clientCommandHandler;
    }

    public UserParameters getUserParameters() {
        return userParameters;
    }

    public ResponseListener getResponseListener() {
        return responseListener;
    }

    public void start() throws IOException, InterruptedException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = socket.getLocalAddress();
        Integer port = socket.getLocalPort();
        String id = ip.toString() + port.toString();

        clientConnect = new ClientConnect(getUserParameters().get(PROPERTY_SERVER_HOST),
                getUserParameters().getInt(PROPERTY_SERVER_PORT));
        this.clientCommandHandler = new ClientCommandHandler(getScanner(), getClientConnect(), id);
//        this.responseListener = new ResponseListener(clientConnect.getAsyncStub(), id);

        Thread t1 = new Thread(getClientCommandHandler());
//        Thread t2 = new Thread(getResponseListener());
        t1.start();
//        t2.start();
        t1.join();
//        t2.join();
    }

}
