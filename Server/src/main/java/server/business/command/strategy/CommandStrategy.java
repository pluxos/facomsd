package server.business.command.strategy;

import server.commons.chord.Node;
import server.commons.domain.GenericCommand;

public interface CommandStrategy {

	void executeCommand(GenericCommand inputParams);

	void passCommand(GenericCommand inputParams, Node node);
}