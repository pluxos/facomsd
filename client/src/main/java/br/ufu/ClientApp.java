package br.ufu;

import br.ufu.client.SocketClient;
import br.ufu.handler.ClientCommandHandler;

import java.io.IOException;
import java.util.Scanner;

import static br.ufu.util.Constants.PROPERTY_SERVER_HOST;
import static br.ufu.util.Constants.PROPERTY_SERVER_PORT;
import static br.ufu.util.UserParameters.get;
import static br.ufu.util.UserParameters.getInt;

public class ClientApp {

    public static void main(String[] args) throws IOException, InterruptedException {

        SocketClient socketClient = new SocketClient();
        socketClient.startConnection(get(PROPERTY_SERVER_HOST), getInt(PROPERTY_SERVER_PORT));

        Scanner scanner = new Scanner(System.in);

        ClientCommandHandler clientCommandHandler = new ClientCommandHandler(scanner, socketClient);

        Thread t = new Thread(clientCommandHandler);

        t.start();
        t.join();

    }

}
