package com.sd.app;

import java.io.DataInputStream;
import java.net.Socket;

public class ClienteMsg implements Runnable {
  Socket connection;
  
  public ClienteMsg( Socket connection ) {
    this.connection = connection;
  }
    
  public void run() {
    try {
      DataInputStream input = new DataInputStream(connection.getInputStream());
      String msgServidor;
      
      while(true) {
        try {
        	
          msgServidor = input.readUTF();
          System.out.println("\nMSG SERVIDOR::: " + msgServidor);
        }
        catch(Exception e) {
          System.out.println("Erro: " + e.getMessage());
          break;
        }
      }
    }
    catch(Exception e) {
      System.out.println("Erro: " + e.getMessage());
    }
    // input.close();
    System.out.println("Conexão encerrada");
    
  }
}