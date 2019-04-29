package client.commons.domain;

import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;

public enum Method {
    GET, CREATE, UPDATE, DELETE, SAIR;
    
    public static Method getMethod(String command) throws InvalidCommandException {
		try {
			return Method.valueOf(command.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new InvalidCommandException(ErrorMap.INVALID_COMMAND);
		}
    }
}