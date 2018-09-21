package cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import servidor.Dados;

public class Client implements Runnable {
  public static void main(String args[]) {
    try {
     
        Thread t = new Thread(new Client());
        t.start();
      
    } catch (Exception e) {
    }
  }
  
  public void run() {
    try {
      Long numero;
      Scanner s = new Scanner(System.in);
      Socket socket = new Socket("127.0.0.1", 9876);
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

      System.out.println("Numero: ");
      numero = s.nextLong();
      
      out.writeObject(numero);
      
      //System.out.println("Resposta: "+ in.readObject());
      Dados resposta = (Dados) in.readObject();
      System.out.println(resposta.getTexto());
    } catch (Exception e) {
    }
  }
}
