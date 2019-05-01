package server.business.command;

import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;
import server.business.command.strategy.*;
import server.commons.domain.Method;

public class RequestUtils {

    public static CommandStrategy getRequestStrategyByMethod(Method method) {
        switch (method) {
            case CREATE :
                return new CreateUser();
            case UPDATE :
                return new UpdateUser();
            case GET :
                return new GetUser();
            case DELETE :
                return new DeleteUser();
            default :
                throw new InvalidCommandException(ErrorMap.UNDEFINED_METHOD);
        }
    }
}
