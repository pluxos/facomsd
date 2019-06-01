package threads;

import server.ItemFila;
import singletons.F4;

import java.util.concurrent.BlockingQueue;

public class F4Consumer implements Runnable
{
    protected BlockingQueue<ItemFila> f4;
    
    public F4Consumer(){
        f4 = F4.getInstance();
    }

	public void run() {
        try{
			while (true){
				ItemFila obj = f4.take();
                System.out.println(obj.toString());
			}
		}
		catch (Exception e){
			System.out.println( "Erro no consumo de F4: " + e.getMessage() );
		}
        
	}
}