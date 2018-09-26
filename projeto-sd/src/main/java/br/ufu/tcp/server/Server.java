package br.ufu.tcp.server;

import br.ufu.tcp.dao.MessageDAO;
import br.ufu.tcp.resources.SingletonDataLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException{
        Server.startServer();
    }

    public static void startServer() throws IOException{
        System.out.println("Starting...");
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Servidor iniciado. A espera de um cliente conectar...");
        MessageDAO messageDAOInicial = new MessageDAO();
        SingletonDataLoader.getInstance().setMessageDAO(messageDAOInicial);
        SingletonDataLoader.getInstance().openFile();
        SingletonDataLoader.getInstance().readFile();
        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread threadListeningClient = new Thread(new ListeningClient(clientSocket));
            threadListeningClient.start();
            System.out.println("Cliente " + clientSocket.toString() + " conectado.");
        }
    }

}

