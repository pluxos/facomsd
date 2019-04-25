package client.service.validation;

public enum ValidCommands {
    GET, CREATE, UPDATE, DELETE, SAIR;

    public static boolean isValid(String command) {
        for (ValidCommands s: ValidCommands.values()) {
            if (command.equalsIgnoreCase(s.toString())) {
               return true;
            }
        }
        return false;
    }
}
