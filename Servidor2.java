import java.util.concurrent.BlockingQueue;

public class Servidor2 implements Runnable{

    BlockingQueue<Comando> f1, f2, f3;
    Comando c;

    Servidor2(BlockingQueue<Comando> f1, BlockingQueue<Comando> f2, BlockingQueue<Comando> f3){
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
    }
	
	public void run() {
            
        while(true){

            if(!f1.isEmpty()){
                c = f1.poll();
                f3.add(c); // e f3
                f2.add(c); // copia de f1 para f2
            }
        }    
    }
}