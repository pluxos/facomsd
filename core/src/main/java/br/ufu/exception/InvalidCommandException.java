package br.ufu.exception;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String s) {
        super(s);
    }

    public InvalidCommandException(String s, NumberFormatException e) {
        super(s, e);
    }
}
