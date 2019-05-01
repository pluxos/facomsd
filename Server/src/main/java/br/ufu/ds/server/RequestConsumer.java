package br.ufu.ds.server;

/**
 * @author Marcus
 */
public class RequestConsumer extends Consumer {

    public RequestConsumer() {
        super(Queues.getInstance().getRequests());
    }

    @Override
    public void consume(Queues.Command command) {
        Queues.getInstance().getCommands().add(command);
        Queues.getInstance().getLogs().add(command);
    }
}
