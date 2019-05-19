package client.commons.utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;

import client.commons.domain.Method;
import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;

public class CommandUtils {

	public static String modelateRequest(String request) {
		return request.toLowerCase().trim();
	}

	public static Method getMethodByUserInput(String userInput) {
		if (!StringUtils.isEmpty(userInput)) {
			String command = userInput.split("; ")[0];
			return Method.getMethod(command);
		}
		throw new InvalidCommandException(ErrorMap.EMPTY_INPUT);
	}

	public static String[] getInputParams(String userInput) {
		if (!StringUtils.isEmpty(userInput)) {
			return userInput.split("; ");
		}
		return null;
	}
	
	public static void validateId(String[] userInput) {
		if (StringUtils.isEmpty(userInput[1]) || !userInput[1].matches("[0-9]+")) {
			throw new InvalidCommandException(ErrorMap.INVALID_ID);
		}
	}
	
	public static void validateEmail(String[] userInput) {
		if (StringUtils.isEmpty(userInput[2])) {
			throw new InvalidCommandException(ErrorMap.INVALID_EMAIL);
		}
		try {
			new InternetAddress(userInput[2]).validate();
		} catch (AddressException e) {
			throw new InvalidCommandException(ErrorMap.INVALID_EMAIL);
		}
	}
	
	public static void validatePassword(String[] userInput) {
		if (StringUtils.isEmpty(userInput[3]) || !userInput[3].matches("^[a-zA-Z0-9]+$")) {
			throw new InvalidCommandException(ErrorMap.INVALID_PASSWORD);
		}
	}
	
	public static void validateName(String[] userInput) {
		if (StringUtils.isEmpty(userInput[4]) || !userInput[4].matches("[a-zA-Z]+")) {
			throw new InvalidCommandException(ErrorMap.INVALID_NAME);
		}
	}
	
	public static void validateUserFields(String[] userInput) {
		validateEmail(userInput);
		validatePassword(userInput);
		validateName(userInput);
	}
}