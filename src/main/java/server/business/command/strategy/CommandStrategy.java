package server.business.command.strategy;

import server.commons.domain.GenericCommand;

public interface CommandStrategy {

	void executeCommand(GenericCommand inputParams);
}