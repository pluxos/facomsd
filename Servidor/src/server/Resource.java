package server;

import java.net.InetAddress;

public class Resource {
    private String request;
    private InetAddress client;
    private int port;
    private boolean isGrpc;

    public Resource(String request, InetAddress client, int port, boolean isGrpc){
        this.client = client;
        this.port = port;
        this.request = request;
        this.isGrpc = isGrpc;
    }

    public Boolean isGrpc() {
        return isGrpc;
    }

    public void setGrpc(Boolean grpc) {
        isGrpc = grpc;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public InetAddress getClient() {
        return client;
    }

    public void setClient(InetAddress client) {
        this.client = client;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
}
