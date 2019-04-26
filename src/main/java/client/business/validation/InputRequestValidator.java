package client.business.validation;

import org.apache.commons.lang3.StringUtils;

import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;
import client.commons.validation.CommandsValidator;

public class InputRequestValidator {

    public static void validateInput(String input) throws InvalidCommandException {
        if (StringUtils.isEmpty(input)) {
            throw new InvalidCommandException(ErrorMap.EMPTY_INPUT);
        }

        if (!CommandsValidator.commandIsValid(input)) {
        	throw new InvalidCommandException(ErrorMap.INVALID_COMMAND);
        }
    }
}
