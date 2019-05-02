package server.business.command.strategy;

import server.client.ResponseSender;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;

import java.io.PrintStream;

public interface CommandStrategy {

	GenericResponse executeCommand(GenericCommand inputParams);
	
	default void makeResponse(GenericResponse response, PrintStream output) throws ServerException {
		String json = JsonUtils.serialize(response);
		ResponseSender.send(json, output);
	}
}