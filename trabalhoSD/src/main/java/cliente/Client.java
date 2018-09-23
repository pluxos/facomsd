package cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import servidor.Dados;

public class Client implements Runnable {
	
	static Semaphore mutex = new Semaphore(1);
	
  public static void main(String args[]) {
    try {
    	Thread cliente = new Thread(new Client());
        cliente.start();
      
    } catch (Exception e) {
    }
  }
  
  @SuppressWarnings("deprecation")
public void run() {
    try {
    	Scanner s = new Scanner(System.in);
        Socket socket = new Socket("127.0.0.1", 9876);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        
        String menu = (String) in.readObject();
        
    	Thread tRead = new Thread(new ClientSend(socket, s, out, menu));
       
        tRead.start();
        Thread tResponse = new Thread(new ClientResponse(socket, in));
        tResponse.start();
        
        if(!tRead.isAlive()) {
        	Thread.sleep(5000);
        	tResponse.stop();
        }
        
        

    } catch (Exception e) {
    }
  }
}
