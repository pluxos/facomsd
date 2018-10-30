package br.ufu.listener;

import br.ufu.exception.ListenerException;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import br.ufu.util.UserParameters;
import io.grpc.examples.servergreeting.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;

@SuppressWarnings("squid:S2142")
public class F4Listener extends FxListener {

    private static final Logger log = LogManager.getLogger(F4Listener.class);

    private final QueueService queueService;
    private final BigInteger serverBand;

    public F4Listener(QueueService queueService, BigInteger serverBand) {
        this.queueService = queueService;
        this.serverBand = serverBand;
    }

    @Override
    protected void listen() {
        try {
            Command item = queueService.consumeF4();
            log.info("F4 Listener take command [{}]", item.getExecuteCommand());
            Response resp = Response.newBuilder().setResp(item.getExecuteCommand()).build();
            item.getObserver().onNext(resp);
            item.getObserver().onCompleted();
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
            throw new ListenerException(e);
        }
    }
}
