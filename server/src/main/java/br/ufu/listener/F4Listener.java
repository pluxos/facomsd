package br.ufu.listener;

import br.ufu.connection.ClientConnection;
import br.ufu.exception.ListenerException;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import io.grpc.examples.servergreeting.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;

@SuppressWarnings("squid:S2142")
public class F4Listener extends FxListener {

    private static final Logger log = LogManager.getLogger(F4Listener.class);

    private final QueueService queueService;
    private final BigInteger serverBand;
    private final ClientConnection leftServer;
    private final ClientConnection rightServer;

    public F4Listener(QueueService queueService, BigInteger serverBand) {
        this.queueService = queueService;
        this.serverBand = serverBand;
        this.leftServer = new ClientConnection("127.0.0.1", 4232);
        this.rightServer = new ClientConnection("127.0.0.1", 4231);
    }

    private void passResponsability(Command item) {
        leftServer.send(item.getExecuteCommand(), item.getObserver());
        System.out.println("Msg enviada para o servidor left");
    }

    @Override
    protected void listen() {
        try {
            Command item = queueService.consumeF4();
            log.info("F4 Listener take command [{}]", item.getExecuteCommand());
            passResponsability(item);
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
            throw new ListenerException(e);
        }
    }
}
