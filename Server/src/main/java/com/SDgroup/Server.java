package com.SDgroup;

import java.net.ServerSocket;
import java.net.Socket;

class Server {
  
  public static void main(String[] args) {
    try {
      Properties properties = new Properties();
      FileInputStream propsFS = new FileInputStream("Server/src/main/resources/Constants.prop");
      properties.load(propsFS);
      
      Integer port = Integer.parseInt(properties.getProperty("port"));
      
      ServerSocket server = new ServerSocket(port);
      System.out.println("Listening on port " + port);
      
      new Thread(new Consumidor()).start();
      
      while( true ) {
        Socket client = server.accept();
        new Thread( new EntryPoint( client ) ).start();         
      }
    }
    catch(Exception e) {
      System.out.println("Erro: " + e.getMessage());
    }
  }
}