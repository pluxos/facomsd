import java.util.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.IOException;

public class ClienteListener implements Runnable {
    
    Thread t;
    String res;
    ClienteListener(Thread t){
        this.t = t;
    }
	
	public void run() {
      
		try{
			InetAddress ip = InetAddress.getByName("localhost");
			Socket listener = new Socket(ip, 5678);

            DataInputStream dis = new DataInputStream(listener.getInputStream());
			
			while (true){

                if(!t.isAlive()){
                    while(true){
                        Thread.sleep(5000);
                        if((res = dis.readUTF()) == ""){
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