package br.ufu.listener;

import br.ufu.exception.ListenerException;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("squid:S2142")
public class F1Listener extends FxListener {

    private static final Logger log = LogManager.getLogger(F1Listener.class);

    private final QueueService queueService;

    public F1Listener(QueueService queueService) {
        this.queueService = queueService;
    }

    @Override
    protected void listen() {
        try {
            Command item = queueService.consumeF1();
            log.info("F1 Listener take command [{}]", item.getExecuteCommand());
            queueService.produceF2(item);
            queueService.produceF3(item);
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
            throw new ListenerException(e);
        }
    }
}
