package server.client;

import java.io.PrintStream;

public class ResponseSender {
    public static void send(String json, PrintStream output) {
        output.println(json);
    }
}
