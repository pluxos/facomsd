package threads;

import server.ItemFila;
import singletons.F4;

import java.util.concurrent.BlockingQueue;
import java.math.BigInteger;
import java.lang.*;

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

    try {
			while (true){
				ItemFila obj = f4.take();
				
				int indice = this.table.getMaisProximo( new BigInteger( obj.getKey() ) );

				String nextIP = this.table.table[indice][2];
				int nextPort = Integer.parseInt(this.table.table[indice][3]);

				String command = obj.getControll();
				System.out.println("TENTANDO ENVIAR PARA O SERVIDOR " + nextPort);
          
				if( command.equals( "CREATE" ) ) {            
				  new Thread( new Create(nextIP, nextPort, new BigInteger(obj.getKey()),  obj.getValue(), obj.getResponseC()) ).start();
				}
	  
				if( command.equals( "UPDATE" ) ) {
				  new Thread( new Update(nextIP, nextPort, new BigInteger(obj.getKey()),  obj.getValue(), obj.getResponseU()) ).start();
				}
				
				if( command.equals( "READ" ) ) {
				  new Thread( new Read(nextIP, nextPort, new BigInteger(obj.getKey()), obj.getResponseR()) ).start();
				}
				
				if( command.equals( "DELETE" ) ) {
				  new Thread( new Delete(nextIP, nextPort, new BigInteger(obj.getKey()), obj.getResponseD()) ).start();
				}
                // System.out.println(obj.toString());
			}
		}
		catch (Exception e){
			System.out.println( "Erro no consumo de F4: " + e.getMessage() );
		}
        
	}
}