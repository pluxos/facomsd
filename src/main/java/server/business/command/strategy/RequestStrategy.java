package server.business.command.strategy;

import client.commons.utils.JsonUtils;
import client.connector.CommandSender;
import client.connector.GenericRequest;

import java.io.PrintStream;

public interface RequestStrategy {

	boolean executeCommand(String[] inputParams);
	
	default void makeRequest(GenericRequest request, PrintStream output) {
		String json = JsonUtils.serialize(request);
		CommandSender.send(json, output);
	}
}