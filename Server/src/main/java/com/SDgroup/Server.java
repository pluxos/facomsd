package com.SDgroup;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

class Server{
  public static void main(String argv[]) throws Exception {
    Properties properties = new Properties();
    FileInputStream propsFS = new FileInputStream("src/main/resources/Constants.prop");
    properties.load(propsFS);
    
    Integer port = Integer.parseInt(properties.getProperty("port"));
    
    
    // ServerSocket ssock = new ServerSocket(port);
    System.out.println("Listening on port " + port);
   
    while (true) {
      // o método accept() bloqueia a execução até que
      // o servidor receba um pedido de conexão    
      //   Socket sock = ssock.accept();
      Thread.currentThread().sleep(1000*20);
      System.out.println("enviando um nova thread");
      new Thread(new EntryPoint(null)).start(); 
     
      
      
    }
    
    
  }
  
  
  
}