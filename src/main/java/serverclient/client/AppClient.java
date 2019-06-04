package serverclient.client;

import java.util.logging.Logger;

import serverclient.client.threads.Client;
import serverclient.model.Message;

public class AppClient {

    private static final Logger logger = Logger.getLogger(AppClient.class.getName());

    public static void main(String[] args) throws Exception {
        Client client;

        if (args.length == 0) {
            client = new Client("localhost", 42420);
        } else {
            client = new Client("localhost", Integer.parseInt(args[0]));
        }

        while (true) {
            Message msgRequest = client.getTerminalView().startReadMessage();
            Message msgResponse = client.sendRequisitionReceiveAnswer(msgRequest);

            client.getTerminalView().readMessage(msgResponse);
        }
    }
}
