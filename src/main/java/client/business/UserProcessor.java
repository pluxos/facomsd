package client.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;

import client.business.request.strategy.RequestStrategy;
import client.business.request.utils.RequestUtils;
import client.commons.domain.Method;
import client.commons.exceptions.InvalidCommandException;
import client.commons.utils.CommandUtils;
import client.commons.utils.JsonUtils;
import client.connector.GenericRequest;

public class UserProcessor {
	
	public static void sendCommand(String input, PrintStream output) throws InvalidCommandException, IOException {
		String[] inputParams = CommandUtils.getInputParams(input);
		if (inputParams != null && !StringUtils.isEmpty(inputParams[0])) {
			Method method = Method.getMethod(inputParams[0]);
			RequestStrategy strategy = RequestUtils.getRequestStrategyByMethod(method);
			GenericRequest request = strategy.buildRequest(inputParams);
			
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File("comandos.txt"),true));
			pw.append(JsonUtils.serialize(request));
			pw.close();
			
			strategy.makeRequest(request, output);
		}
	}
}