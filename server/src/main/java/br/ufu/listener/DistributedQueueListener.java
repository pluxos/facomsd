package br.ufu.listener;

import br.ufu.connection.AtomixConnection;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;


public class DistributedQueueListener {

    private static final Logger log = LogManager.getLogger(DistributedQueueListener.class);
    private static final String SPACE = " ";

    private final AtomixConnection atomixConnection;
    private final QueueService queueService;
    private final Integer serverPort;

    public DistributedQueueListener(AtomixConnection atomixConnection, QueueService queueService, Integer serverPort) {
        this.atomixConnection = atomixConnection;
        this.queueService = queueService;
        this.serverPort = serverPort;
    }

    public void startDistributedQueueListener() throws InterruptedException, ExecutionException {
        atomixConnection.getAtomixReplica().getQueue("commands")
                .thenCompose(m -> m.onAdd(event -> receivedCommand(event.value()))).get();
    }

    private void receivedCommand(Object value) {
        String stringValue = (String) value;
        log.info("Received message from Distributed Queue: {}", stringValue);

        int index = stringValue.lastIndexOf(SPACE);
        String command = stringValue.substring(0, index);

        if (!checkResponsibility(index, stringValue)) {
            Command item = new Command(command, null);
            queueService.produceF3(item);
        }
    }

    private boolean checkResponsibility(int index, String value) {
        Integer port = Integer.parseInt(value.substring(index+1));
        return port.equals(serverPort);
    }

}
