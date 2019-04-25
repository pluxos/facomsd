package client.service.validation;

import client.commons.validation.CommandsValidator;

public class CommandRequestValidator {

    public static void validateCommand(String command) throws Exception {
        /* Validar comando vazio */
        if (CommandsValidator.commandIsEmpty(command)) {
            throw new Exception("Comando vazio");
        }

        //Verificar se comando é válido, via classe em commons
        if (!CommandsValidator.commandIsValid(command)) {
            throw new Exception("Comando não é valido");
        }
    }
}
