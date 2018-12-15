package br.ufu.listener;

import br.ufu.connection.AtomixConnection;
import br.ufu.exception.InvalidCommandException;
import br.ufu.model.Command;
import br.ufu.service.CrudService;
import br.ufu.service.QueueService;
import br.ufu.util.CommandUtil;
import br.ufu.communication.Response;
import io.atomix.Atomix;
import io.atomix.AtomixReplica;
import io.atomix.collections.DistributedQueue;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

import static org.apache.commons.lang3.StringUtils.join;

public class F5Listener {

    private static final Logger log = LogManager.getLogger(F5Listener.class);

    private final AtomixConnection atomixConnection;
    private final QueueService queueService;

    public F5Listener(AtomixConnection atomixConnection, QueueService queueService) {
        this.atomixConnection = atomixConnection;
        this.queueService = queueService;
    }

    public void startDistributedQueueListener() {
        System.out.println("here");
        while(atomixConnection.getAtomixReplica() == null) {
            System.out.println("replica is null");
        }
        atomixConnection.getAtomixReplica().getQueue("commands")
                .thenCompose(m -> m.onAdd(event -> printCommand(event.value()))).join();
    }

    protected void printCommand(Object command) {
        log.info("Received message from Distributed Queue: {}", command.toString());
//        Command item = new Command(command, null);
//        queueService.produceF2(item);
//        queueService.produceF3(item);
    }

}
