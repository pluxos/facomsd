package threads;

import configuration.Properties;
import model.Socket;
import threads.ThreadSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Logger;

public class ThreadServer extends Thread
{
	private static String PORT;
    private static String IPADDRESS;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    @Override
    public void run()
    {
        PORT = Properties.getInstance().loadProperties().getProperty("server.port");
        IPADDRESS = Properties.getInstance().loadProperties().getProperty("server.address");
        logger.info("Porta do server = "+ PORT);

        byte[] receiveData = new byte[1480];
        DatagramSocket serverSocket = null;
        try 
        {
            serverSocket = new DatagramSocket(Integer.parseInt(PORT));
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        }
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        Thread threadProcess = new ThreadSocket(serverSocket);
        threadProcess.start();

        while(true) 
        {
            try 
            {
                serverSocket.receive(receivePacket);

                byte[] result = receivePacket.getData();

                getInstance().addArrivingSocket(new Socket(receivePacket.getPort(),result));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
