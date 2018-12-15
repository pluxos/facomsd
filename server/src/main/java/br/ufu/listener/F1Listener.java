package br.ufu.listener;

import br.ufu.connection.AtomixConnection;
import br.ufu.exception.ListenerException;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import br.ufu.util.CommandUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;

@SuppressWarnings("squid:S2142")
public class F1Listener extends FxListener {

    private static final Logger log = LogManager.getLogger(F1Listener.class);

    private final QueueService queueService;
    private final BigInteger smallerKey;
    private final BigInteger serverId;
    private final AtomixConnection atomixConnection;

    public F1Listener(QueueService queueService, BigInteger serverId, BigInteger smallerKey, AtomixConnection atomixConnection) {
        this.smallerKey= smallerKey;
        this.serverId = serverId;
        this.queueService = queueService;
        this.atomixConnection = atomixConnection;
    }

    private boolean checkResponsibility(BigInteger value) {
        return value.compareTo(smallerKey) >= 0 && serverId.compareTo(value) >= 0;
    }

    @Override
    protected void listen() {
        try {
            Command item = queueService.consumeF1();
            String command = item.getExecuteCommand();
            log.info("F1 Listener take command [{}]", command);
            if (checkResponsibility(new BigInteger(CommandUtil.getKey(command)))) {
                atomixConnection.getAtomixReplica().getQueue("commands")
                        .thenCompose(m -> m.add(item.getExecuteCommand()))
                        .thenRun(() -> System.out.println("comando enviado para recurso"))
                        .join();
            } else {
                queueService.produceF4(item);
            }
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
            throw new ListenerException(e);
        }
    }
}
