package server.business.command.strategy;

import server.commons.domain.GenericCommand;

public class DeleteUser implements CommandStrategy {

	@Override
	public boolean executeCommand(GenericCommand inputParams) {
		System.out.println("DELETE USER" + inputParams.getCode());
		return true;
	}
}