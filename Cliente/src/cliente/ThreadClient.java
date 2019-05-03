package cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ThreadClient extends Thread
{
    DatagramSocket clientSocket;
    byte[] receiveData = new byte[1400];
    
    public void run() 
    {
	try 
	{
            this.clientSocket = new DatagramSocket();
	    DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
	    while (true) 
	    {
	      	try
	      	{
                    this.clientSocket.receive(receivedPacket);
	        } 
	        catch (IOException e) 
	        {
                    e.printStackTrace();
	        }
	        if (receivedPacket.getData() != null) 
	        {
	            String response = new String(receivedPacket.getData(), receivedPacket.getOffset(), receivedPacket.getLength());
	            System.out.println("Resposta " + response);
	        }
	    }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
}