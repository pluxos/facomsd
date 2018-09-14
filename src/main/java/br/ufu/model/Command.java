package br.ufu.model;

import br.ufu.handler.ClientHandler;

public class Command {

    private String executeCommand;

    private ClientHandler clientHandler;

    public Command(String executeCommand, ClientHandler clientHandler) {
        this.executeCommand = executeCommand;
        this.clientHandler = clientHandler;
    }

    public String getExecuteCommand() {
        return executeCommand;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
