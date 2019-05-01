package server.business.command.strategy;

import server.commons.domain.GenericCommand;

public class UpdateUser implements CommandStrategy {

	@Override
	public boolean executeCommand(GenericCommand inputParams) {
		System.out.println("UPDATE USER" + inputParams.getCode());
		return true;
	}
}