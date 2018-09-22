package br.ufu.exception;

import java.io.IOException;

public class ClientCommandHandlerException extends RuntimeException {
    public ClientCommandHandlerException(IOException e) {
        super(e);
    }
}
