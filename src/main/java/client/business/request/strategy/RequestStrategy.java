package client.business.request.strategy;

import java.io.IOException;
import java.io.PrintStream;

import client.commons.exceptions.InvalidCommandException;
import client.commons.utils.JsonUtils;
import client.connector.CommandSender;
import client.connector.GenericRequest;

public interface RequestStrategy {

	GenericRequest buildRequest(String[] inputParams) throws InvalidCommandException, IOException;
	
	default void makeRequest(GenericRequest request, PrintStream output) {
		String json = JsonUtils.serialize(request);
		CommandSender.send(json, output);
	}
}