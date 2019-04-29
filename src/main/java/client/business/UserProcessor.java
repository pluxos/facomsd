package client.business;

import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.lang3.StringUtils;

import client.business.request.strategy.RequestStrategy;
import client.business.request.utils.RequestUtils;
import client.commons.domain.Method;
import client.commons.exceptions.InvalidCommandException;
import client.connector.GenericRequest;

public class UserProcessor {
	
	public static void sendCommand(String input, PrintStream output) throws InvalidCommandException, IOException {
		String[] inputParams = null;
		if (!StringUtils.isEmpty(input)) {
			inputParams = input.split("; ");
		}
		if (inputParams != null && !StringUtils.isEmpty(inputParams[0])) {
			Method method = Method.getMethod(inputParams[0]);
			RequestStrategy strategy = RequestUtils.getRequestStrategyByMethod(method);
			GenericRequest request = strategy.buildRequest(inputParams);
			strategy.makeRequest(request, output);
		}
	}
}