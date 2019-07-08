package br.com.ufu.ClientServer.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ReceptionThread implements Runnable {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final BlockingQueue<Input> queue;

    public ReceptionThread(Socket _socket, ObjectInputStream _inputStream, ObjectOutputStream _outputStream, BlockingQueue<Input> _queue) {
        this.socket = _socket;
        this.inputStream = _inputStream;
        this.outputStream = _outputStream;
        this.queue = _queue;
    }

    public void run() {
        try {
            while (true) {
                queue.put(new Input(outputStream, inputStream.readObject().toString()));
            }
        } catch (IOException e) {
            System.out.println("Conexão com o cliente " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " encerrada!");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
