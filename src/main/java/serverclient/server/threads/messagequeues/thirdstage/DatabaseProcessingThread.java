package serverclient.server.threads.messagequeues.thirdstage;

import serverclient.server.services.MessageService;
import serverclient.server.services.impl.MessageServiceImpl;
import serverclient.server.threads.ServerThread;

public class DatabaseProcessingThread implements Runnable {

    private static MessageService messageService;

    static {
        messageService = new MessageServiceImpl();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                messageService.processMessage(ServerThread.getFila3().take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
