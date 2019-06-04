package serverclient.server.threads.messagequeues.firststage;

import serverclient.server.threads.ServerThread;
import serverclient.server.threads.handlers.MessageData;

import java.util.logging.Logger;

public class FirstQueueThread implements Runnable{

    private final static Logger LOGGER = Logger.getLogger(FirstQueueThread.class.getName());

    private volatile MessageData message;

    public FirstQueueThread(MessageData message) {
        this.message = message;
    }

    @Override
    public void run() {

        try {
            LOGGER.info("Mensagem " + this.message.getMessage() + " será colocada na Fila1.");
            ServerThread.getFila1().put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
