package com.sd.app;

/**
 *
 * @author Douglas S dos Anjos
 */
import java.net.ServerSocket;
import java.net.Socket;

class Server {
  
  public static void main(String[] args) {
    try {

      Integer port = 4000;

      ServerSocket server = new ServerSocket(port);
      System.out.println("Listening on port " + port);

      new Thread(new Consumidor()).start();
      new Thread(new Logger()).start();
      new Thread(new Kv()).start();

      while( true ) {
        Socket client = server.accept();
        new Thread( new ClientHandler( client ) ).start();
      }
    }
    catch(Exception e) {
      System.out.println("Erro: " + e.getMessage());
    }
  }
}