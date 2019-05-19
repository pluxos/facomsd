package server.commons.exceptions;

public class ServerException extends Exception {
    private static final long serialVersionUID = 1L;

    private ErrorMap error;

    public ServerException(ErrorMap error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return error.getMessage();
    }
}
