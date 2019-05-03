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

        try
        {
            socket = new ServerSocket(12345);            
            Scanner fromClient;
            PrintStream toClient;
            String message;
            
            while(true)
            {
                connection = socket.accept();
                System.out.println("-----------------------------------------------------");
                System.out.println("\nRecebendo conexao de " + connection + "...");
                
                
                fromClient = new Scanner(connection.getInputStream());
                message = fromClient.nextLine();
                
                System.out.println("Mensagem recebida: " + message);
                
                toClient = new PrintStream(connection.getOutputStream());
                toClient.println("Mensagem recebida");

                connection.close();
            }
            
        }
        catch(Exception e)
        {
            System.out.println("Erro S-01: " + e.getMessage());
            System.exit(0);
        }
    }    
}