package br.ufu.listener;

import br.ufu.connection.ClientConnection;
import br.ufu.exception.ListenerException;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import br.ufu.communication.Response;
import br.ufu.util.CommandUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;

@SuppressWarnings("squid:S2142")
public class F4Listener extends FxListener {

    private static final Logger log = LogManager.getLogger(F4Listener.class);

    private final QueueService queueService;
    private final ClientConnection leftServer;
    private final ClientConnection rightServer;
    private final BigInteger serverId;
    private final BigInteger maxKey;

    public F4Listener(QueueService queueService, Integer leftServer,
                      Integer rightServer, BigInteger serverId, BigInteger maxKey) {

        this.queueService = queueService;
        this.serverId =  serverId;
        this.maxKey = maxKey;
        this.leftServer = new ClientConnection("127.0.0.1", leftServer);
        this.rightServer = new ClientConnection("127.0.0.1", rightServer);
    }

    private void passResponsability(Command item) {
        BigInteger key = new BigInteger(CommandUtil.getKey(item.getExecuteCommand()));
        BigInteger right, left;
        if (key.compareTo(serverId) == 1) {     // key bigger than server
            right = key.subtract(serverId);
            left = serverId.add(maxKey.subtract(key));
        } else {
            right = maxKey.subtract(serverId.add(key));
            left = serverId.subtract(key);
        }
        if (left.compareTo(right) <= 0) {
            leftServer.send(item.getExecuteCommand(), item.getObserver());
            log.info("Command '" +item.getExecuteCommand()+ "' sent to server on left. Port: {}",
                    leftServer.getPort());
        } else {
            rightServer.send(item.getExecuteCommand(), item.getObserver());
            log.info("Command '" +item.getExecuteCommand()+ "' sent to server on right. Port: {}",
                    rightServer.getPort());
        }
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
