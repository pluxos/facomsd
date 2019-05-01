import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.net.*;

public class Servidor4 implements Runnable{

    BlockingQueue<Comando> f3;
    Comando c;

    Servidor4(BlockingQueue<Comando> f3){
        this.f3 = f3;
    }
	
	public void run() {
            
        while(true){

            if(!f3.isEmpty()){
                c = f3.poll();
                // operacão com banco(mapa) aqui
            }
        }    
    }
}