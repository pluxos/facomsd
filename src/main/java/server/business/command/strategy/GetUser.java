package server.business.command.strategy;

import server.commons.domain.GenericCommand;

public class GetUser implements CommandStrategy {

	@Override
	public boolean executeCommand(GenericCommand inputParams) {
		System.out.println("GET USER" + inputParams.getCode());
		return true;
	}
}