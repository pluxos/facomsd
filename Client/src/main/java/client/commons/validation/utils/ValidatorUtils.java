package client.commons.validation.utils;

import client.commons.domain.Method;
import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;
import client.commons.validation.strategy.*;

public class ValidatorUtils {

	public static ValidatorStrategy getStrategyByMethod(Method method) {
		switch (method) {
		case CREATE :
			return new CreateValidator();
		case UPDATE :
			return new UpdateValidator();
		case DELETE :
			return new DeleteValidator();
		case GET :
			return new GetValidator();
		default :
			throw new InvalidCommandException(ErrorMap.UNDEFINED_METHOD);
		}
	}
}