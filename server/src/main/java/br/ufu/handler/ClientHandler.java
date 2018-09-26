package br.ufu.handler;

import br.ufu.model.Command;
import br.ufu.service.QueueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {


    private static final Logger log = LogManager.getLogger(ClientHandler.class);

    private final QueueService queueService;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(QueueService queueService, Socket socket) {
        this.clientSocket = socket;
        this.queueService = queueService;
    }

    @Override
    public void run() {
        try {

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String command;
            while ((command = in.readLine()) != null) {
                queueService.produceF1(new Command(command, this));
                log.info("Command REQUESTED: {}", command);
            }
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public void sendResponse(String response) {
        out.println(response);
        out.flush();
    }
}
