package client.commons.validation.utils;

import client.commons.domain.Method;
import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;
import client.commons.validation.strategy.CreateValidator;
import client.commons.validation.strategy.DeleteValidator;
import client.commons.validation.strategy.GetValidator;
import client.commons.validation.strategy.UpdateValidator;
import client.commons.validation.strategy.ValidatorStrategy;

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