package server.receptor;

import server.commons.Rows.RowF1;
import server.commons.Rows.RowF2;
import server.commons.Rows.RowF3;
import server.commons.domain.GenericCommand;

public class ConsumerF1 implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(;;) {
			try {
				GenericCommand g = RowF1.getFifo().take();
				RowF2.addItem(g);
				RowF3.addItem(g);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

}
