package server.receptor;

import server.business.command.RequestUtils;
import server.business.command.strategy.CommandStrategy;
import server.commons.Rows.RowF3;
import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;

public class ThreadCommand implements Runnable {

	@Override
	public void run() {
		for (;;) {
			try {
				GenericCommand genericCommand = RowF3.getFifo().take();
				Method method = Method.getMethod(genericCommand.getMethod());
				CommandStrategy command = RequestUtils.getRequestStrategyByMethod(method);
				GenericResponse response = command.executeCommand(genericCommand);

				command.makeResponse(response, genericCommand.getOutput());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ServerException e) {
				e.printStackTrace();
			}
		}
	}
}