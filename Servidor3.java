import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.net.*;

public class Servidor3 implements Runnable{

    BlockingQueue<Comando> f2;
    Comando c;

    Servidor3(BlockingQueue<Comando> f2){
        this.f2 = f2;
    }
	
	public void run() {
            
        while(true){

            if(!f2.isEmpty()){
                c = f2.poll();
                // operacão com arquivo aqui
            }
        }    
    }
}