package server;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.logging.Logger;

import static server.DataStorage.getInstance;

public class ThreadProcess extends Thread {

    private DatagramSocket serverSocket;
    private static Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

    ThreadProcess( DatagramSocket serverSocket ) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run(){

        ThreadLogger threadLogger = ThreadLogger.init();
        List< Operation > operations = threadLogger.getLogList();
        if( operations != null ) {
            for ( Operation operation : operations ) {
                ThreadExecute.executeOperation( operation );

                String message = " EXECUTE: " +
                                 " Op     : " + operation.getOperation() +
                                 " Key   : " + operation.getKey() +
                                 " Value : " + operation.getValue();
                System.out.println( message );
            }
        }

        while( true ) {
            try {
                if( !getInstance().getArriving().isEmpty() ) {

                    Arriving arriving = getInstance().pollArriving();
                    Operation operation = SerializeState.readOperation(arriving.getPackage());

                    System.out.println( operation.toString() );
                    Thread thread = new ThreadExecute( serverSocket, arriving.getmPort(), operation );
                    getInstance().addToRun( thread.getId() );
                    thread.start();
                }
            } catch ( IOException e ) { System.out.println(e.getMessage()); }
        }
    }
}
