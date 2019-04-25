package client.commons.validation;

import client.service.validation.ValidCommands;

public class CommandsValidator {
    public static boolean commandIsEmpty(String str) {
        return str.isEmpty();
    }

    public static boolean commandIsValid(String str) {
        String command = str.split("; ")[0];

        return ValidCommands.isValid(command);
    }
}
