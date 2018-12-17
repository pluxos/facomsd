package br.ufu.listener;

import br.ufu.connection.ClientConnection;
import br.ufu.exception.ListenerException;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import br.ufu.util.CommandUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.List;

@SuppressWarnings("squid:S2142")
public class F4Listener extends FxListener {

    private static final Logger log = LogManager.getLogger(F4Listener.class);
    private static final String LOCALHOST = "127.0.0.1";
    private static final String RIGHT = "RIGHT";
    private static final String LEFT = "LEFT";

    private final QueueService queueService;
    private ClientConnection leftServer;
    private ClientConnection rightServer;
    private final BigInteger serverId;
    private final BigInteger maxKey;
    private final List<Integer> leftServerList;
    private final List<Integer> rightServerList;

    public F4Listener(QueueService queueService, List<Integer> leftServerList,
                      List<Integer> rightServerList, BigInteger serverId, BigInteger maxKey) {

        this.queueService = queueService;
        this.serverId =  serverId;
        this.maxKey = maxKey;
        this.leftServerList = leftServerList;
        this.rightServerList = rightServerList;
        this.leftServer = new ClientConnection(LOCALHOST, leftServerList.get(0));
        this.rightServer = new ClientConnection(LOCALHOST, rightServerList.get(0));
    }

    public void changeLeftServerAndSendAgain(Command item) {
        int currentIndex = leftServerList.indexOf(this.leftServer.getPort());
        int index = (currentIndex == leftServerList.size() - 1)
                ? 0 : currentIndex + 1;
        shutdownServerOn(LEFT);

        leftServer = new ClientConnection(LOCALHOST, leftServerList.get(index));
        log.info("Left server changed to port : {}", leftServer.getPort());

        sendToLeftServer(item);
    }

    public void changeRightServerAndSendAgain(Command item) {
        int currentIndex = rightServerList.indexOf(this.rightServer.getPort());
        int index = currentIndex == rightServerList.size() - 1
                ? 0 : currentIndex + 1;
        shutdownServerOn(RIGHT);

        rightServer = new ClientConnection(LOCALHOST, rightServerList.get(index));
        log.info("Right server changed to port : {}", rightServer.getPort());

        sendToRightServer(item);
    }

    private void shutdownServerOn(String server) {
        try {
            if (server.equals(RIGHT)) {
                rightServer.shutdown();
            } else {
                leftServer.shutdown();
            }
        } catch (InterruptedException e) {
            log.warn("Old server on "+ server.toLowerCase() +" failed to shutdown: {}", e);
        }
    }

    private void sendToLeftServer(Command item) {
        leftServer.send(item, this, LEFT);
        log.info("Command '" +item.getExecuteCommand()+ "' sent to server on left. Port: {}",
                leftServer.getPort());
    }

    private void sendToRightServer(Command item) {
        rightServer.send(item, this, RIGHT);
        log.info("Command '" +item.getExecuteCommand()+ "' sent to server on right. Port: {}",
                rightServer.getPort());
    }

    private void passResponsability(Command item) throws InterruptedException {
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
            sendToLeftServer(item);
        } else {
            sendToRightServer(item);
        }
    }

    public ClientConnection getLeftServer() {
        return leftServer;
    }

    public ClientConnection getRightServer() {
        return rightServer;
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
