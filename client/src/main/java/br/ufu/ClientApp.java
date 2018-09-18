package br.ufu;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.start();
    }

}
