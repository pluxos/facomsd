package br.ufu.exception;

public class SnapshotException extends RuntimeException {
    public SnapshotException(Exception e) {
        super(e);
    }

    public SnapshotException(InterruptedException e) {
        super(e);
    }
}
