package br.ufu.listener;

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
    private final BigInteger leftRestriction;
    private final BigInteger rightRestriction;

    public F1Listener(QueueService queueService, BigInteger serverId, BigInteger serverBand) {
        BigInteger interval = serverBand.divide(new BigInteger("2"));
        this.leftRestriction = serverId.subtract(interval);
        this.rightRestriction = serverId.add(interval);
        this.queueService = queueService;
    }

    private boolean checkResponsibility(BigInteger value) {
        if (value.compareTo(leftRestriction) == 1
                && (rightRestriction.compareTo(value) == 1
                    || value.equals(rightRestriction))) {
            return true;
        }
        return false;
    }

    @Override
    protected void listen() {
        try {
            Command item = queueService.consumeF1();
            log.info("F1 Listener take command [{}]", item.getExecuteCommand());
            if (checkResponsibility(new BigInteger(
                    CommandUtil.getKey(item.getExecuteCommand())))) {
                queueService.produceF2(item);
                queueService.produceF3(item);
            } else {
                queueService.produceF4(item);
            }
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
            throw new ListenerException(e);
        }
    }
}
