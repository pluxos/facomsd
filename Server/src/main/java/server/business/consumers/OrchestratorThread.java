package server.business.consumers;

import server.commons.chord.Chord;
import server.commons.domain.GenericCommand;
import server.commons.rows.RowF1;
import server.commons.rows.RowF2;
import server.commons.rows.RowF3;
import server.commons.rows.RowF4;

public class OrchestratorThread implements Runnable {

	@Override
	public void run() {
		for(;;) {
			try {
				GenericCommand g = RowF1.getFifo().take();
				if (Chord.getChodNode().getRange().contains(g.getCode().intValue())) {
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
