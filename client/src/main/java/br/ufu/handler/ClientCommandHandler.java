package br.ufu.handler;

import br.ufu.client.SocketClient;
import br.ufu.exception.ClientCommandHandlerException;
import br.ufu.exception.InvalidCommandException;
import br.ufu.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

import static br.ufu.util.Constants.*;

public class ClientCommandHandler implements Runnable {

    private static final Logger log = LogManager.getLogger(ClientCommandHandler.class);
    private final Scanner scanner;
    private final SocketClient socketClient;

    public ClientCommandHandler(Scanner scanner, SocketClient socketClient) {
        this.scanner = scanner;
        this.socketClient = socketClient;
    }

    private static void validateCommand(String command) throws InvalidCommandException {
        String[] commandSplited = command.split(COMMAND_SEPARATOR);
        String action = commandSplited[0];

        if (getValidCommands().stream().noneMatch(e -> e.equals(action))) {
            throw new InvalidCommandException(String.format(MESSAGE_INVALID_COMMAND, action, getValidCommands()));
        }

        validateCommandParams(commandSplited);
    }

    private static void validateCommandParams(String[] commands) throws InvalidCommandException {
        String action = commands[0];
        switch (action) {
            case COMMAND_CREATE:
                validateParams(commands, BIG_INTEGER_PARSER, STRING_PARSER);
                break;
            case COMMAND_READ:
                validateParams(commands, BIG_INTEGER_PARSER);
                break;
            case COMMAND_UPDATE:
                validateParams(commands, BIG_INTEGER_PARSER, STRING_PARSER);
                break;
            case COMMAND_DELETE:
                validateParams(commands, BIG_INTEGER_PARSER);
                break;
            default:
                String message = String.format("Não há como verificar argumentos  de comando invalido. [%s]", action);
                throw new InvalidCommandException(message);
        }
    }

    private static void validateParams(String[] commands, Parser... parsers) throws InvalidCommandException {
        if (commands.length != parsers.length + 1) {
            throw new InvalidCommandException(MESSAGE_PARAMETROS_INSUFICIENTES);
        }
        for (int i = 0; i < parsers.length; i++) {
            parsers[i].parse(commands[i + 1]);
        }

    }

    public SocketClient getSocketClient() {
        return socketClient;
    }

    @Override
    public void run() {
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            System.out.println(command);
            if (COMMAND_SAIR.equals(command)) {
                stopConnection();
                break;
            }
            try {
                validateCommand(command);
                sendMessage(command);
            } catch (InvalidCommandException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void stopConnection() {
        try {
            getSocketClient().stopConnection();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new ClientCommandHandlerException(e);
        }
    }

    private void sendMessage(String message) {
        try {
            String response = getSocketClient().sendMessage(message);
            log.info(response);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            throw new ClientCommandHandlerException(e);
        }
    }
}
