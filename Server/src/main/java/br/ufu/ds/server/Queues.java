package br.ufu.ds.server;

import br.ufu.ds.rpc.Request;
import br.ufu.ds.rpc.Response;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Marcus
 */
public final class Queues {

    private BlockingQueue<Command> mQueueRequest,
                                  mQueueCmd,
                                  mQueueLog,
                                  mQueueAnotherServer;

    private static Queues instance = null;

    private Queues() {
        mQueueCmd = new ArrayBlockingQueue<>(20, true);
        mQueueLog = new ArrayBlockingQueue<>(20, true);
        mQueueRequest = new ArrayBlockingQueue<>(20, true);
        mQueueAnotherServer = new ArrayBlockingQueue<>(20, true);
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

    public BlockingQueue<Command> getAnotherServer() {
        return mQueueAnotherServer;
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
        public final StreamObserver<Response> client;
        public final Request request;

        public Command(StreamObserver<Response> client, Request request) {
            this.client = client;
            this.request = request;
        }
    }
}
