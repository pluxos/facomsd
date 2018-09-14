package br.ufu.exception;

public class ListenerException extends RuntimeException {
    public ListenerException(Exception e) {
        super(e);
    }

    public ListenerException(InvalidCommandException e) {
        super(e);
    }
}
