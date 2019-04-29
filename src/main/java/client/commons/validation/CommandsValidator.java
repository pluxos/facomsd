package client.commons.validation;

import org.apache.commons.lang3.StringUtils;

import client.commons.domain.Method;
import client.commons.exceptions.InvalidCommandException;

public class CommandsValidator {
   
    public static boolean commandIsValid(String input) throws InvalidCommandException {
    	if (!StringUtils.isEmpty(input)) {
    		String command = input.split("; ")[0];
            return Method.getMethod(command) != null;
    	}
    	return false;
    }
}
