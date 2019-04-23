package client.commons.validation;

public class CommandsValidator {
    public static boolean validateCommand(String command) {
        if(command.isEmpty()){
            return false;
        }

        return true;
    }
}
