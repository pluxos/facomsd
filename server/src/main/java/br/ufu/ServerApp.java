package br.ufu;

public class ServerApp {

    public static void main(String[] args) throws Exception {
        Server server = new Server(args);
        server.start();
    }

}
