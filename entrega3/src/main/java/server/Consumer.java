package server;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable
{
	private static BlockingQueue<Operation> f1;
    private static BlockingQueue<Operation> f2;
	private static BlockingQueue<Operation> f3;
	
	public Consumer(BlockingQueue<Operation> F1, BlockingQueue<Operation> F2, BlockingQueue<Operation> F3)
	{
		this.f1 = F1;
		this.f2 = F2;
		this.f3 = F3;
	}
	
	public void run() 
	{
		try
		{
			while(true)
			{
				Operation i = f1.take();
				f2.put(i);
				f3.put(i);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
