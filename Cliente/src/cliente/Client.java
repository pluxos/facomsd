package cliente;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client 
{
    public static void main(String[] args) throws IOException 
    {       
	Socket cliente;
	Thread threadc;
		
	Scanner s = new Scanner(System.in);
        Scanner toServer;
        
	int option;
	int valor;
	int chave;
        String command;
        boolean aux = true;
		
	try
	{
            cliente = new Socket("localhost", 12345);
            
            Runnable r = new ThreadClient(cliente);            
            threadc = new Thread(r);
            threadc.start();
            
            toServer = new Scanner(cliente.getInputStream());
            PrintStream output;
            String fromServer;
			
            System.out.println("\n################## Trabalho de SD - Parte 1 ##################");
            do
            {
		System.out.println("	\nMenu de opcoes\n");
		System.out.println("1 - CREATE");
		System.out.println("2 - READ");
		System.out.println("3 - UPDATE");
		System.out.println("4 - DELETE");
		System.out.println("5 - Sair");
		
		System.out.println("\nOpcao: ");
		option = s.nextInt();
				
		switch(option)
		{
                    case 1:
			System.out.println("\nChave: ");
			chave = s.nextInt();
                        
			System.out.println("\nValor: ");
			valor = s.nextInt();
                        
                        command = "CREATE " + Integer.toString(chave) + " " + Integer.toString(valor);                        
                        System.out.println("\nOpcao enviada: " + command);
                        
                        output = new PrintStream(cliente.getOutputStream());
                        output.println(command);
                        
                        fromServer = toServer.nextLine();
                        System.out.println(fromServer);
                        
			break;
						
                    case 2:
			System.out.println("\nChave: ");
			chave = s.nextInt();
                        
                        command = "READ " + Integer.toString(chave);
                        System.out.println("\nOpcao enviada: " + command);
                        
                        
                        output = new PrintStream(cliente.getOutputStream());
                        output.println(command);
                        
                        fromServer = toServer.nextLine();
                        System.out.println(fromServer);
                        
			break;
						
                    case 3:
			System.out.println("\nChave");
			chave = s.nextInt();
                        
			System.out.println("\nValor: ");
			valor = s.nextInt();
                        
                        command = "UPDATE " + Integer.toString(chave) + " " + Integer.toString(valor);
                        System.out.println("\nOpcao enviada: " + command);
                        
                        output = new PrintStream(cliente.getOutputStream());
                        output.println(command);
                        
                        fromServer = toServer.nextLine();
                        System.out.println(fromServer);
                        
			break;
						
                    case 4:
			System.out.println("\nChave: ");
			chave = s.nextInt();
                        
                        command = "CREATE " + Integer.toString(chave);
                        System.out.println("\nOpcao enviada: " + command);
                        
                        output = new PrintStream(cliente.getOutputStream());
                        output.println(command);
                        
                        fromServer = toServer.nextLine();
                        System.out.println(fromServer);
                        
			break;
                        
                    case 5:
                        System.out.println("\nSaindo...");
                        aux = false;
                        break;
						
                    default:
			System.out.println("\nOpcao Invalida! Tente novamente");
			break;
		}
            }
            while(aux);
	}
	catch(Exception e)
	{
            System.out.println("Erro C-01: " + e.getMessage());
            System.exit(-1);
	}
    }
}
