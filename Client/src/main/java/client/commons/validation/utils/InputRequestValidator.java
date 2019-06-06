package client.commons.validation.utils;

import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;
import client.commons.utils.CommandUtils;
import org.apache.commons.lang3.StringUtils;

public class InputRequestValidator {

    public static void validateInput(String input) throws InvalidCommandException {
        if (StringUtils.isEmpty(input)) {
            throw new InvalidCommandException(ErrorMap.EMPTY_INPUT);
        }
        ValidatorUtils.getStrategyByMethod(CommandUtils.getMethodByUserInput(input)).validate(input);
    }
}