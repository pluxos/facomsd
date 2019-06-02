package server.business.command;

import server.commons.exceptions.ErrorMap;
import server.business.command.strategy.*;
import server.commons.domain.Method;
import server.commons.exceptions.InvalidCommandException;

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
                throw new InvalidCommandException(ErrorMap.BAD_REQUEST);
        }
    }
}
