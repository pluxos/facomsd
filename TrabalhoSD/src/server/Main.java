package server;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

import static server.DataStorage.*;
import static server.SerializeState.*;

public class Main {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main( String args[] ) {
        String port = "8088";
        String ip_address = "localhost";
        logger.info("Server port: "+ port);

        byte[] receiveData = new byte[1480];
        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket( Integer.parseInt( port ) );
        } catch ( SocketException e ) { e.printStackTrace(); }

        DatagramPacket receivePacket = new DatagramPacket( receiveData, receiveData.length );

        Thread thread = new ThreadProcess( serverSocket );
        thread.start();

        while( true ) {
            try {
                serverSocket.receive( receivePacket );

                byte[] result = receivePacket.getData();

                getInstance().addArriving( new Arriving( receivePacket.getPort(), result ) );
            } catch ( IOException e ) { e.printStackTrace(); }
        }
    }
}
