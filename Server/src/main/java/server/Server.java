package server;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

class Server {
  
  public static void main(String[] args) {
    try {
      // Properties properties = new Properties();
      // FileInputStream propsFS = new FileInputStream("Server/src/main/resources/Constants.prop");
      // properties.load(propsFS);

      Integer port = 12345;// Integer.parseInt(properties.getProperty("port"));

      ServerSocket server = new ServerSocket(port);
      System.out.println("Listening on port " + port);

      new Thread(new Consumidor()).start();
      new Thread(new Logger()).start();

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