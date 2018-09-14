package br.ufu.listener;

import br.ufu.model.Command;
import br.ufu.exception.ListenerException;
import br.ufu.service.QueueService;
import br.ufu.writer.LogWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class F2Listener extends FxListener {

    private static final Logger log = LogManager.getLogger(F2Listener.class);

    private final QueueService queueService;

    private final LogWriter logWriter;

    public F2Listener(QueueService queueService, LogWriter logWriter) {
        this.queueService = queueService;
        this.logWriter = logWriter;
    }

    @Override
    protected void listen() {
        try {
            Command item = queueService.consumeF2();
            log.info("F2 Listener take command [{}]", item.getExecuteCommand());
            logWriter.write(item.getExecuteCommand());
        } catch (InterruptedException | IOException e) {
            throw new ListenerException(e);
        }
    }
}
