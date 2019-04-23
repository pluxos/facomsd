package client.commons.validation;

public class CommandsValidator {
    public static boolean validateCommand(String command) {
        return !command.isEmpty();
    }
}
