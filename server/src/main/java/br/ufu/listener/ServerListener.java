package br.ufu.listener;

import br.ufu.exception.ListenerException;
import br.ufu.handler.ClientHandlerSocket;
import br.ufu.service.QueueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

@SuppressWarnings("squid:S2189")
public class ServerListener implements Runnable {

    private static final Logger log = LogManager.getLogger(ServerListener.class);

    private final QueueService queueService;
    private final int port;
    private ServerSocket serverSocket;
    private boolean running = true;

    public ServerListener(QueueService queueService, int port) {
        this.port = port;
        this.queueService = queueService;
    }

    private void start(int port) throws IOException {
        log.info("Running socket on {}", port);
        serverSocket = new ServerSocket(port);
        while (running)
            new ClientHandlerSocket(queueService, serverSocket.accept()).start();
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        try {
            start(port);
        } catch (IOException e) {
            throw new ListenerException(e);
        }
    }

}
