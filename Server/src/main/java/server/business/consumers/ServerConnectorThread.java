package server.business.consumers;

import server.business.command.RequestUtils;
import server.business.command.strategy.CommandStrategy;
import server.commons.chord.ChodNode;
import server.commons.chord.Chord;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.rows.RowF4;

public class ServerConnectorThread implements Runnable {
    @Override
    public void run() {
        for(;;) {
            try {
                GenericCommand genericCommand = RowF4.getFifo().take();
                ChodNode chodNode = Chord.getFt().catchResponsibleNode(genericCommand.getCode().intValue());

                Method method = Method.getMethod(genericCommand.getMethod());
                CommandStrategy command = RequestUtils.getRequestStrategyByMethod(method);
                command.passCommand(genericCommand, chodNode);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
