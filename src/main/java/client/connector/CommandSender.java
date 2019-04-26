package client.connector;

import java.io.PrintStream;

public class CommandSender {

	public static void send(String json, PrintStream output) {
        output.println(json);
    }
}