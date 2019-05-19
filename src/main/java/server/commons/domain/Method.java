package server.commons.domain;

import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;

public enum Method {
    GET, CREATE, UPDATE, DELETE;

    public static Method getMethod(String command) {
        try {
            return Method.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidCommandException(ErrorMap.UNDEFINED_METHOD);
        }
    }
}
