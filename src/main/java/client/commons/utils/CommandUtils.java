package client.commons.utils;

import org.apache.commons.lang3.StringUtils;

import client.commons.domain.Method;
import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;

public class CommandUtils {

	public static String modelateRequest(String request){
        return request.toLowerCase().trim();
    }
	
	public static Method getMethodByUserInput(String userInput) throws InvalidCommandException {
		if (!StringUtils.isEmpty(userInput)) {
    		String command = userInput.split("; ")[0];
            return Method.getMethod(command);
    	}
		throw new InvalidCommandException(ErrorMap.EMPTY_INPUT);
	}
}