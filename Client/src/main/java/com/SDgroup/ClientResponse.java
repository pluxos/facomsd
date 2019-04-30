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
      int lengthWrong;
      int lengthRight;
      int type;
      String x = null;
      
      while(true) {
        try {
          lengthWrong = input.readInt();
          lengthRight = (int) lengthWrong/10;
          type = lengthWrong - (lengthRight*10);
          
          if( lengthRight > 0 ) {
              message = new byte[lengthRight];
              input.readFully(message, 0, lengthRight);
          }
          
          if( type == 1 ) {
            x = new String(message);
          }
          else if ( type == 2 ) {
            x = new String(message);
          }
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