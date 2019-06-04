package serverclient.client.producer;

import serverclient.client.threads.producer.CommandReaderThread;
import serverclient.model.MessageOld;

import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

public class CommandReaderThreadTest extends CommandReaderThread {

    private volatile BlockingQueue<MessageOld> novasMensagens;

    public CommandReaderThreadTest(Boolean producerIsOver, ObjectOutputStream objectOutputStream, BlockingQueue<MessageOld> novasMensagens) {
        super();
        this.producerIsOver = producerIsOver;
        this.objectOutputStream = objectOutputStream;
        this.novasMensagens = novasMensagens;
    }

    @Override
    public void run() {

        while (!this.exit && !Thread.interrupted()) {

            MessageOld message = null;
            try {
                message = this.novasMensagens.take();
            } catch (InterruptedException e) {
                LOGGER.info(e.getMessage());
                this.shutdown();
                continue;
            }

            this.sendMessage(message);

            if (message.getLastOption() == 5) {
                this.shutdown();
            }

        }

    }

}
