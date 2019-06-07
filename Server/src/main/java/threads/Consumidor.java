package threads;

import server.ItemFila;
import singletons.F1;
import singletons.F2;
import singletons.F3;
import singletons.F4;

import java.util.concurrent.BlockingQueue;

public class Consumidor implements Runnable {
	protected BlockingQueue<ItemFila> f1;
	protected BlockingQueue<ItemFila> f2;
	protected BlockingQueue<ItemFila> f3;
	protected BlockingQueue<ItemFila> f4;

	public Consumidor() {
		this.f1 = F1.getInstance();
		this.f2 = F2.getInstance();
		this.f3 = F3.getInstance();
		this.f4 = F4.getInstance();
	}

	public void run() {
		try {
			while (true) {
                ItemFila obj = f1.take();
                // if(obj!=null) System.out.println("asdfsdf");
				if (obj.getOurResponsability()) {
					f2.put(obj);
					f3.put(obj);
				}else f4.put(obj);

			}
		} catch (Exception e) {
			System.out.println("Erro no consumo de F1: " + e.getMessage());
		}
	}
}