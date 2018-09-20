package cliente;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
  public static void main(String args[]) {
    try {
      for (int i = 0; i < 3; i++) {
        Thread t = new Thread(new Client());
        t.start();
      }
    } catch (Exception e) {
    }
  }
  
  public void run() {
    try {
      int numero, temp;
      Scanner s = new Scanner(System.in);
      Socket socket = new Socket("127.0.0.1", 9876);
      Scanner s1 = new Scanner(socket.getInputStream());
      PrintStream p = new PrintStream(socket.getOutputStream());
      System.out.println("Numero: ");
      numero = s.nextInt();
      p.println(numero);
      temp = s1.nextInt();
      System.out.println("temp:  " + temp);
    } catch (Exception e) {
    }
  }
}
