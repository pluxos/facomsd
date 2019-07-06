package server.business.command.strategy;

import server.commons.chord.ChodNode;
import server.commons.domain.GenericCommand;

public interface CommandStrategy {

	void executeCommand(GenericCommand inputParams);

	void passCommand(GenericCommand inputParams, ChodNode chodNode);
}