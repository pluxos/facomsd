package client.business;

import io.grpc.GreeterGrpc;
import org.apache.commons.lang3.StringUtils;

import client.business.request.strategy.RequestStrategy;
import client.business.request.utils.RequestUtils;
import client.commons.domain.Method;
import client.commons.exceptions.InvalidCommandException;
import client.commons.utils.CommandUtils;

class UserProcessor {
	
	static void sendCommand(String input, GreeterGrpc.GreeterBlockingStub output) throws InvalidCommandException {
		String[] inputParams = CommandUtils.getInputParams(input);
		if (inputParams != null && !StringUtils.isEmpty(inputParams[0])) {
			Method method = Method.getMethod(inputParams[0]);
			RequestStrategy strategy = RequestUtils.getRequestStrategyByMethod(method);
			strategy.sendRequest(inputParams, output);
		}
	}
}