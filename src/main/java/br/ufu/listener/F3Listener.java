package br.ufu.listener;

import br.ufu.model.Command;
import br.ufu.exception.InvalidCommandException;
import br.ufu.exception.ListenerException;
import br.ufu.service.CrudService;
import br.ufu.service.QueueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class F3Listener extends FxListener {

    private static final Logger log = LogManager.getLogger(F3Listener.class);

    private final QueueService queueService;

    private final CrudService crudService;

    public F3Listener(QueueService queueService, CrudService crudService) {
        this.queueService = queueService;
        this.crudService = crudService;
    }

    @Override
    protected void listen() {
        try {
            Command item = queueService.consumeF3();
            log.info("F3 Listener take command [{}]", item.getExecuteCommand());
            String response = crudService.execute(item.getExecuteCommand());
            item.getClientHandler().sendResponse(String.format("Command RESPONSE: %s", response));
        } catch (InterruptedException | InvalidCommandException e) {
            throw new ListenerException(e);
        }
    }
}
