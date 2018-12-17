package br.ufu.listener;

import br.ufu.connection.AtomixConnection;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;


public class F5Listener {

    private static final Logger log = LogManager.getLogger(F5Listener.class);

    private final AtomixConnection atomixConnection;
    private final QueueService queueService;
    private final Integer serverPort;

    public F5Listener(AtomixConnection atomixConnection, QueueService queueService, Integer serverPort) {
        this.atomixConnection = atomixConnection;
        this.queueService = queueService;
        this.serverPort = serverPort;
    }

    public void startDistributedQueueListener() throws InterruptedException, ExecutionException {
        System.out.println("here");
        while(atomixConnection.getAtomixReplica() == null) {
            System.out.println("replica is null");
        }
        atomixConnection.getAtomixReplica().getQueue("commands")
                .thenCompose(m -> m.onAdd(event -> printCommand(event.value()))).get();
    }

    protected void printCommand(Object value) {
        String stringValue = (String) value;
        log.info("Received message from Distributed Queue: {}", stringValue);

        int index = stringValue.lastIndexOf(" ");
        String command = stringValue.substring(0, index);
        System.out.println("----------------------"+command);

        if (!checkResponsability(index, stringValue)) {
            Command item = new Command(command, null);
            queueService.produceF2(item);
            queueService.produceF3(item);
        }
    }

    public boolean checkResponsability(int index, String value) {
        Integer port = Integer.parseInt(value.substring(index+1));
        return port == serverPort;
    }

}
