package com.SDgroup;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

class Server {

  public static void main(String[] args) {
    Properties properties = new Properties();
    FileInputStream propsFS;
    try {
      propsFS = new FileInputStream("Server/src/main/resources/Constants.prop");
      properties.load(propsFS);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Integer port = Integer.parseInt(properties.getProperty("port"));

    ServerSocket ssock = null;

    new Thread(new Consumidor()).start(); 

    try {
      ssock = new ServerSocket(port);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Listening on port " + port);

    while (true) {
      // o método accept() bloqueia a execução até que
      // o servidor receba um pedido de conexão
      Socket sock = null;
      try {
        sock = ssock.accept();
      } catch (IOException e) {
        e.printStackTrace();
      }
      new Thread(new EntryPoint(sock)).start(); 
    }
    
    
  }
  
  
  
  
}