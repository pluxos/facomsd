package server.commons.exceptions;

public enum ErrorMap {
    BAD_REQUEST("400 - Bad Request"), INTERNAL_SERVER_ERROR("500 - Internal Server Error");

    private String message;

    ErrorMap(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
