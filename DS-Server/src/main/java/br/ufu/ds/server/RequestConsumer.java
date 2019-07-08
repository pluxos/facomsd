package br.ufu.ds.server;

/**
 * @author Marcus
 */
public final class RequestConsumer extends Consumer {

    public RequestConsumer() {
        super(Queues.getInstance().getRequests());
    }

    @Override
    public void consume(Queues.Command command) {
        try {
            Queues.getInstance().getCommands().put(command);
            Queues.getInstance().getLogs().put(command);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
