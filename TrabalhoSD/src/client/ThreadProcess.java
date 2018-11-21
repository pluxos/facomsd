package client;

import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.util.logging.Logger;

public class ThreadProcess extends Thread{

    final private BigInteger key;
    final private String value;
    final private Integer menuOption;

    ThreadProcess(BigInteger key, String value, Integer menuOption){
        this.key = key;
        this.value = value;
        this.menuOption = menuOption;
    }

    @Override
    public void run(){

        Operation operation = new Operation( key, value, menuOption );

        byte[] data = operation.convertData();
        byte[] receivedData = new byte[1480];

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName( "localhost" );
            DatagramPacket sendPacket = new DatagramPacket( data, data.length, IPAddress, Integer.parseInt("8088") );

            clientSocket.send( sendPacket );

            DatagramPacket receivedPacket = new DatagramPacket( receivedData, receivedData.length );
            clientSocket.receive( receivedPacket );

            String data1 = new String( receivedPacket.getData(), receivedPacket.getOffset(), receivedPacket.getLength() );
            System.out.println("Response from server: " + data1);

            clientSocket.close();
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }
}
