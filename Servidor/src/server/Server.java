package server;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server 
{
    public static void main(String[] args) 
    {
        ServerSocket socket;
        Socket connection;
        Thread server;

        try
        {
            socket = new ServerSocket(12345);            
                        
            System.out.println("\nEsperando Conexao...\n");
            
            while(true)
            {            
                connection = socket.accept();
                server = new Thread(new ThreadServer(connection));
                server.start();
            }
            
        }
        catch(Exception e)
        {
            System.out.println("Erro S-01: " + e.getMessage());
            System.exit(0);
        }
    }    
}
