package br.ufu.model;

import io.grpc.examples.servergreeting.Response;
import io.grpc.stub.StreamObserver;

public class Command {

    private String executeCommand;

    private StreamObserver<Response> observer;

    public Command(String executeCommand, StreamObserver<Response> observer) {
        this.executeCommand = executeCommand;
        this.observer = observer;
    }

    public String getExecuteCommand() {
        return executeCommand;
    }

    public StreamObserver<Response> getObserver() {
        return observer;
    }
}
