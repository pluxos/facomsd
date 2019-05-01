package server.receptor;

import server.business.command.RequestUtils;
import server.business.command.strategy.CommandStrategy;
import server.commons.Rows.RowF3;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;

public class ThreadCommand implements Runnable {

	@Override
	public void run() {
		// Executa Comandos no hashMap
		for (;;) {
			try {
				GenericCommand genericCommand = RowF3.getFifo().take();
				Method method = Method.getMethod(genericCommand.getMethod());
				CommandStrategy command = RequestUtils.getRequestStrategyByMethod(method);
				command.executeCommand(genericCommand);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
