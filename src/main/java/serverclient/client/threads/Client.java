package serverclient.client.threads;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import serverclient.client.view.TerminalView;
import serverclient.model.Message;
import serverclient.model.MessageServiceProtGrpc;

import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final ManagedChannel channel;
    private MessageServiceProtGrpc.MessageServiceProtBlockingStub blockingStub;

    private final TerminalView terminalView;

    public Client(String hostname, int port) {
        channel = ManagedChannelBuilder.forAddress(hostname, port)
                .build();
        blockingStub = MessageServiceProtGrpc.newBlockingStub(channel);
        terminalView = new TerminalView();
    }

    public void shutdown() {
        logger.info("Shutting down ... ");
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            channel.shutdownNow();
        }
    }

    public Message sendRequisitionReceiveAnswer(Message msgRequest) {

        Timer timer = null;
        Message answer = null;

        try {

            final int FIVE_SECONDS = 5000;

            timer = new Timer(FIVE_SECONDS, event -> {
                this.shutdown();
            });

            timer.start();

            if (msgRequest.getLastOption() == 1) {
                answer = blockingStub.createMessage(msgRequest);
            } else if (msgRequest.getLastOption() == 2) {
                answer = blockingStub.readMessage(msgRequest);
            } else if (msgRequest.getLastOption() == 3) {
                answer = blockingStub.updateMessage(msgRequest);
            } else if (msgRequest.getLastOption() == 4) {
                answer = blockingStub.deleteMessage(msgRequest);
            } else if (msgRequest.getLastOption() == 5) {
                timer.stop();
                this.shutdown();
            }

            timer.stop();

        } catch (Exception e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
            this.shutdown();
        }

        return answer;
    }

    public TerminalView getTerminalView() {
        return terminalView;
    }
}