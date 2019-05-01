import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.io.*;
import java.net.*;

public class Servidor3 implements Runnable{

    BlockingQueue<Comando> f2;
    
    Servidor3(BlockingQueue<Comando> f2){
        this.f2 = f2;
    }
	
	public void run() {
        
        try {
            Comando c;
            Comando a;
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("log.txt", true));
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("log.txt"));
            
            while(true){
                
                if(!f2.isEmpty()){
                    c = f2.poll();
                    if(c.cmd != 2){ //ignora Reads
                        // operacão com arquivo
                        oos.writeObject(c);
                    }
                            
                }
            }    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}