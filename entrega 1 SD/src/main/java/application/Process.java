package application;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Process {
    private String request;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket clientSocket;


    public Process(String data, InputStream inputStream, OutputStream outputStream, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.request = data;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
