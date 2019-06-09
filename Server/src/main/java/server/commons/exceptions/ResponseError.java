package server.commons.exceptions;

import java.io.PrintStream;

public class ResponseError {

    public static void sendError(PrintStream output, String error) {
        output.println(error);
    }
}
