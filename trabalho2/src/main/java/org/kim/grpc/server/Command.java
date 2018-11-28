package org.kim.grpc.server;

import io.grpc.stub.StreamObserver;
import org.kim.grpc.ServerResponse;

public class Command {

    private String type;
    private Long key;
    private String data;
    private StreamObserver<ServerResponse> observer;

    public Command(String type, Long key, String data, StreamObserver<ServerResponse> observer) {
        this.type = type;
        this.key = key;
        this.data = data;
        this.observer = observer;
    }

    public Command(String type, Long key, StreamObserver<ServerResponse> observer) {
        this.type = type;
        this.key = key;
        this.observer = observer;
    }

    public String getType() {
        return type;
    }

    public Long getKey() {
        return key;
    }

    public String getData() {
        return data;
    }

    public StreamObserver<ServerResponse> getObserver() {
        return observer;
    }

    @Override
    public String toString() {
        return "Command{" +
                "type='" + type + '\'' +
                ", key=" + key +
                ", data='" + data + '\'' +
                ", observer=" + observer +
                '}';
    }
}
