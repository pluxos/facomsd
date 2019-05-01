package br.ufu.ds.server;


import java.util.concurrent.BlockingQueue;

/**
 * @author Marcus
 */
public abstract class Consumer implements Runnable {

    private boolean run = true;
    public abstract void consume(final Queues.Command command);
    private BlockingQueue<Queues.Command> mQueueRequest;

    public Consumer(BlockingQueue<Queues.Command> mQueueRequest) {
        this.mQueueRequest = mQueueRequest;
    }

    @Override
    public final void run() {
        while (run) {
            try {
                Queues.Command cmd = mQueueRequest.take();
                consume(cmd);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public final void stop() {
        this.run = false;
    }
}
