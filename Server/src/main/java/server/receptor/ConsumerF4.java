package server.receptor;

import server.business.command.RequestUtils;
import server.business.command.strategy.CommandStrategy;
import server.commons.Chord.Chord;
import server.commons.Chord.Node;
import server.commons.Rows.RowF4;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;

public class ConsumerF4 implements Runnable {
    @Override
    public void run() {
        for(;;) {
            try {
                GenericCommand genericCommand = RowF4.getFifo().take();
                Node node = Chord.getFt().catchResponsibleNode(genericCommand.getCode().intValue());

                Method method = Method.getMethod(genericCommand.getMethod());
                CommandStrategy command = RequestUtils.getRequestStrategyByMethod(method);
                command.passCommand(genericCommand, node);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
