import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import java.util.*;
import java.lang.*;

public class ClientResponse implements Runnable {
  Socket connection;
  
  public ClientResponse( Socket connection ) {
    this.connection = connection;
  }
  
  @Override
  public void run() {
    try {
      DataInputStream input = new DataInputStream(connection.getInputStream());
      byte[] message = null;
      int length;
      String x;
      
      while(true) {
        try {
          length = input.readInt();
          
          if( length > 0 ) {
            message = new byte[length];
            input.readFully(message, 0, length);
          }
          x = new String(message);
          System.out.println(x);
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