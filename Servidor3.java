import java.util.concurrent.BlockingQueue;
import java.io.*;

public class Servidor3 implements Runnable{

    BlockingQueue<Comando> f2;
    
    Servidor3(BlockingQueue<Comando> f2){
        this.f2 = f2;
    }
	
	public void run() {
        
        try {
            Comando c;
            FileOutputStream f = new FileOutputStream(new File("log.txt"));
            ObjectOutputStream oos = new ObjectOutputStream(f);
            
            while(true){
                
                if(!f2.isEmpty()){
                    c = f2.poll();
                    if(c.cmd != 2){ //ignora Reads
                        // operacão com arquivo
                        oos.writeObject(c);
                    }
                            
                }
                // oos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}