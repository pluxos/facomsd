package server.business.consumers;

import server.business.command.RequestUtils;
import server.business.command.strategy.CommandStrategy;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.rows.RowF3;

public class CommandExecutorThread implements Runnable {

	@Override
	public void run() {
		for (;;) {
			try {
				GenericCommand genericCommand = RowF3.getFifo().take();
				Method method = Method.getMethod(genericCommand.getMethod());
				CommandStrategy command = RequestUtils.getRequestStrategyByMethod(method);
				command.executeCommand(genericCommand);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}