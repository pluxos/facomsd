package br.ufu;

public class ClientApp {

    public static void main(String[] args) throws Exception {
        Client client = new Client(args);
        client.start();
    }

}
