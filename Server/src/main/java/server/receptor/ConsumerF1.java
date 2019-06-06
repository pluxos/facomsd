package server.receptor;

import server.commons.Chord.Chord;
import server.commons.Rows.RowF1;
import server.commons.Rows.RowF2;
import server.commons.Rows.RowF3;
import server.commons.Rows.RowF4;
import server.commons.domain.GenericCommand;

public class ConsumerF1 implements Runnable {

	@Override
	public void run() {
		for(;;) {
			try {
				GenericCommand g = RowF1.getFifo().take();
				if (Chord.getNode().getRange().contains(g.getCode().intValue())) {
					RowF2.addItem(g);
					RowF3.addItem(g);
				} else {
					RowF4.addItem(g);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}

}
