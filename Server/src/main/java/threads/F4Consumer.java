package threads;

import server.ItemFila;
import singletons.F4;

import java.util.concurrent.BlockingQueue;
import java.math.BigInteger;

import singletons.*;

import server.*;

public class F4Consumer implements Runnable
{
    protected BlockingQueue<ItemFila> f4;
	public Table table;
	
    public F4Consumer(){
		f4 = F4.getInstance();
		this.table = Table.getInstance();		
    }

	public void run() {

		String nextIP = this.table.table[0][2];
		int nextPort = Integer.parseInt(this.table.table[0][3]);

        try{
			while (true){
				ItemFila obj = f4.take();

				String command = obj.getControll();
          
				if( command.equals( "CREATE" ) ) {            
				  new Thread( new Create(nextIP, nextPort, new BigInteger(obj.getKey()),  obj.getValue()) ).start();
				}
	  
				if( command.equals( "UPDATE" ) ) {
				  new Thread( new Update(nextIP, nextPort, new BigInteger(obj.getKey()),  obj.getValue()) ).start();
				}
				
				if( command.equals( "READ" ) ) {
				  new Thread( new Read(nextIP, nextPort, new BigInteger(obj.getKey())) ).start();
				}
				
				if( command.equals( "DELETE" ) ) {
				  new Thread( new Delete(nextIP, nextPort, new BigInteger(obj.getKey())) ).start();
				}
                // System.out.println(obj.toString());
			}
		}
		catch (Exception e){
			System.out.println( "Erro no consumo de F4: " + e.getMessage() );
		}
        
	}
}