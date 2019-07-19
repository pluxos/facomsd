package com.SistemasDistribuidos.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	 public static void main(String[] args) throws IOException  
	    { 
	        try
	        { 
	            Scanner scanner = new Scanner(System.in); 
	              
	            // pegando localhost ip 
	            InetAddress ip = InetAddress.getByName("localhost"); 
	      
	            // estabelecendo conexão com o servidor porta 1024 
	            Socket socket = new Socket(ip, 1024); 
	      
	            // obtendo stream de entrada e saída 
	            DataInputStream input = new DataInputStream(socket.getInputStream()); 
	            DataOutputStream output = new DataOutputStream(socket.getOutputStream()); 
	      
	            // o loop seguinte realiza a troca de informações entre o manipulador do cliente(ProcessaCliente) e do cliente
	            while (true)  
	            { 
	                System.out.println(input.readUTF()); 
	                String tosend = scanner.nextLine(); 
	                output.writeUTF(tosend); 
	                  
	                // If client sends exit,close this connection  
	                // and then break from the while loop 
	                if(tosend.equals("exit")) 
	                { 
	                    System.out.println("Fechando conexão : " + socket); 
	                    socket.close(); 
	                    System.out.println("Conexão fechada"); 
	                    break; 
	                } 
	                  
	                // printing date or time as requested by client 
	                String received = input.readUTF(); 
	                System.out.println(received); 
	            } 
	              
	            // fechando recursos 
	            scanner.close(); 
	            input.close(); 
	            output.close(); 
	        }catch(Exception e){ 
	            e.printStackTrace(); 
	        } 
	    }
}
