package threads;

import model.ItemFila;
import singletons.F1;
import singletons.F2;
import singletons.F3;

import java.util.concurrent.BlockingQueue;

public class Consumidor implements Runnable
{
	protected BlockingQueue<ItemFila> f1;
	protected BlockingQueue<ItemFila> f2;
	protected BlockingQueue<ItemFila> f3;

	public Consumidor() {
		this.f1 = F1.getInstance();
		this.f2 = F2.getInstance();
		this.f3 = F3.getInstance();
	}

	public void run() {
		try{
			while (true){
				ItemFila obj = f1.take();

				f2.put(obj);
				f3.put(obj);
			}
		}
		catch (Exception e){
			System.out.println( "Erro no consumo de F1: " + e.getMessage() );
		}
	}
}