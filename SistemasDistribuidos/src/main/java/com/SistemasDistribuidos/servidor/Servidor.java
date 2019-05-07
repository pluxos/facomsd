package com.SistemasDistribuidos.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Servidor{
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException { 
        // servidor ouvindo na porta 1024 
        ServerSocket serverSocket = new ServerSocket(1024); 
          
        // rodando loop infinito para pegar a requisição do cliente          
        while (true)  
        { 
            Socket socket = null; 
              
            try 
            { 
                // socket object to receive incoming client requests 
                socket = serverSocket.accept(); 
                  
                System.out.println("Novo cliente conectado : " + socket); 
                  
                // obtendo entrada e saída 
                DataInputStream input = new DataInputStream(socket.getInputStream()); 
                DataOutputStream output = new DataOutputStream(socket.getOutputStream()); 
                  
                System.out.println("Assinando nova thread para o cliente"); 
  
                // criando uma nova thread 
                Thread thread = new ProcessaCliente(socket, input, output); 
  
                // Invocando o método para iniciar 
                thread.start(); 
                  
            } 
            catch (Exception e){ 
                socket.close(); 
                e.printStackTrace(); 
            } 
        } 
    }
	
	public void bdServer(String comando) {
//		Map<BigInteger> bd = new HashMap<Integer, Object>();
//		Queue<String> queue = new LinkedList<String>();
//		queue.add(comando);
	}
}
	