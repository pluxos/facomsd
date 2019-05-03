package server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ThreadServer implements Runnable
{
    Socket connection;
    
    public ThreadServer(Socket s)
    {
        this.connection = s;
    }
    
    @Override
    public void run() 
    {
        Scanner fromClient;
        PrintStream toClient;
        String message;
        
        try
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("\nRecebendo conexao de " + connection + "...");
                
            fromClient = new Scanner(connection.getInputStream());
            message = fromClient.nextLine();
                
            System.out.println("Mensagem recebida: " + message);
                
            toClient = new PrintStream(connection.getOutputStream());
            toClient.println("Mensagem recebida");
        }
        catch(Exception e)
        {
            System.out.println("Erro TS-01: " + e.getMessage());
            System.exit(0);
        }
    }    
}
