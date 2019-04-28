import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import java.util.*;

class Client {
  public static void main(String argv[]) throws Exception {
    try {
      Socket cliente = new Socket("localhost",12345);
      ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
      
      System.out.println(entrada.readObject());
      System.out.println(entrada.readObject());
      System.out.println(entrada.readObject());
      entrada.close();
      System.out.println("Conexão encerrada");
    }
    catch(Exception e) {
      System.out.println("Erro: " + e.getMessage());
    }
  }
}