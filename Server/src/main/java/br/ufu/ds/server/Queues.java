package br.ufu.ds.server;

import br.ufu.ds.ServerProtocol;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Marcus
 */
public final class Queues {

    private BlockingQueue<Command> mQueueRequest,
                                  mQueueCmd,
                                  mQueueLog;

    private static Queues instance = null;

    private Queues() {
        mQueueCmd = new ArrayBlockingQueue<>(20, true);
        mQueueLog = new ArrayBlockingQueue<>(20, true);
        mQueueRequest = new ArrayBlockingQueue<>(20, true);
    }

    public BlockingQueue<Command> getRequests() {
        return mQueueRequest;
    }

    public BlockingQueue<Command> getCommands() {
        return mQueueCmd;
    }

    public BlockingQueue<Command> getLogs() {
        return mQueueLog;
    }

    public static Queues getInstance() {
        if (instance == null) {
            synchronized (Queues.class) {
                if (instance == null)
                    instance = new Queues();
            }
        }

        return instance;
    }

    public static class Command {
        public final SocketChannel client;
        public final ServerProtocol.Request request;

        public Command(SocketChannel client, ServerProtocol.Request request) {
            this.client = client;
            this.request = request;
        }
    }
}
