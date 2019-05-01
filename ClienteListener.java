import java.util.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.File;

public class ClienteListener implements Runnable {
    
    Thread t;
    String res;
    public int port;
    
    ClienteListener(Thread t){
        this.t = t;
    }
	
	public void run() {
      
		try{
            Scanner scanner = new Scanner(new File("port2.txt"));
			while (scanner.hasNextInt()) {
				port = scanner.nextInt();
			}
			InetAddress ip = InetAddress.getByName("localhost");
            Socket listener = new Socket(ip, port);
            scanner.close();

            DataInputStream dis = new DataInputStream(listener.getInputStream());
			
			while (true){

                if(!t.isAlive()){
                    while(true){
                        Thread.sleep(5000);
                        if((res = dis.readUTF()) == ""){
                            System.out.println("Terminando...");
                            listener.close();
                            return;
                        }
                        System.out.println(res);
                    }
                }else{

                    res = dis.readUTF(); // resultado da operação
                    System.out.println(res);
                }
			}
		}catch (IOException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
  }
}