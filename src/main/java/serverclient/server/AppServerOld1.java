package serverclient.server;

import serverclient.server.threads.ServerThread;

import java.util.logging.Logger;

@Deprecated
public class AppServerOld1 {

    private final static Logger LOGGER = Logger.getLogger(AppServerOld1.class.getName());

    public static void main(String[] args) {

//        // Make sure both arguments are present
//        if (args.length < 1) {
//            LOGGER.severe(StringsConstants.ERR_NO_ARGS.toString());
//            System.exit(1);
//        }
//
//        // Try to parse the port number
//        int port = -1;
//        try {
//            port = Integer.parseInt(args[0]);
//        } catch (NumberFormatException nfe) {
//            LOGGER.severe("Invalid listen port value: \"" + args[0] + "\".");
//            LOGGER.severe(StringsConstants.ERR_NO_ARGS.toString());
//            System.exit(1);
//        }
//
//        // Make sure the port number is valid for TCP.
//        if (port <= 0 || port > 65536) {
//            LOGGER.severe("Port value must be in (0, 65535].");
//            System.exit(1);
//        }

        final Thread server = new Thread(new ServerThread(8080));
        server.start();

        try {
            // Wait for the server to shutdown
            server.join();
            LOGGER.info("Completed shutdown.");
        } catch (InterruptedException e) {
            // Exit with an error condition
            LOGGER.severe("Interrupted before accept thread completed.");
            System.exit(1);
        }
    }
}
