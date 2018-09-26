package com.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;

import com.server.commands.DeleteCommand;
import com.server.commands.ReadCommand;
import com.server.commands.ServerCommand;
import com.server.commands.WriteCommand;
import com.utils.StreamUtils;

public class Server {

    public static final int port = 10024;

    public void startUp() throws IOException {
        System.out.println("Iniciando servidor na porta"
                + port);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            try {
                System.out
                        .println("Esperando requisição ...");
                Socket clientSocket = serverSocket.accept();
                inputStream = clientSocket.getInputStream();
                outputStream = clientSocket.getOutputStream();
                ServerCommand serverCommand = dispatchCommand(inputStream);
                System.out
                        .println("Processando requisição: "
                                + serverCommand);
                serverCommand.setInputStream(inputStream);
                serverCommand.setOutputStream(outputStream);
                serverCommand.run();
                StreamUtils.closeSocket(inputStream);
                System.out
                        .println("Requisição executada!");
            } catch (Exception e) {
                System.out
                        .println("Erro! "
                                + e.getMessage());
                StreamUtils.sendError(e.getMessage(), outputStream);
                StreamUtils.closeSocket(inputStream);
            }
        }
    }

    private ServerCommand dispatchCommand(InputStream inputStream)
            throws ServerException {
        try {
            ServerCommand serverCommand = null;
            String operation = StreamUtils.readLine(inputStream);
            if (operation != null) {
                if (operation.equals("write")) {
                    serverCommand = new WriteCommand();
                } else if (operation.equals("read")) {
                    serverCommand = new ReadCommand();
                } else if (operation.equals("delete")) {
                    serverCommand = new DeleteCommand();
                } else {
                    throw new ServerException("Comando não encontrado!");
                }
            }
            return serverCommand;
        } catch (IOException e) {
            System.out
                    .println("Erro! "
                            + e.getMessage());
            throw new ServerException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            System.out
                    .println("Servidor está rodando");
            server.startUp();
        } catch (IOException e) {
            System.out.println("Erro! "
                    + e.getMessage());
        }
    }

}
