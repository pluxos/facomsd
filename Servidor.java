import java.util.*;
import java.net.Socket;
import java.io.IOException;

public class Servidor implements Runnable{
	
	public void run() {
        ServerSocket servidor = new ServerSocket(1234);


    }
		
	
	
	public static void main (String args[]) {
        Thread t1 = new Thread(new Servidor1(), "thread1");
        t1.start();
        Thread t2 = new Thread(new Servidor2(), "thread2");
        t2.start();
        Thread t3 = new Thread(new Servidor3(), "thread3");
        t3.start();
        Thread t4 = new Thread(new Servidor4(), "thread4");
		t4.start();

	}
	
	
	
	
	
}