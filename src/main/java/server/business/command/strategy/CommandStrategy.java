package server.business.command.strategy;

import client.commons.utils.JsonUtils;
import client.connector.CommandSender;
import server.commons.domain.GenericCommand;

import java.io.PrintStream;

public interface CommandStrategy {

	boolean executeCommand(GenericCommand inputParams);
	
	default void makeRequest(GenericCommand request, PrintStream output) {
		String json = JsonUtils.serialize(request);
		CommandSender.send(json, output);
	}
}