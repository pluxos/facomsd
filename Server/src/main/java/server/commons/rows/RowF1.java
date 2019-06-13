package server.commons.rows;

import java.util.concurrent.LinkedBlockingQueue;

import server.commons.domain.GenericCommand;

public class RowF1 {
	
	private static volatile LinkedBlockingQueue<GenericCommand> f1 = new LinkedBlockingQueue<>();

	public static LinkedBlockingQueue<GenericCommand> getFifo() {
		return f1;
	}

	public static synchronized void addItem(GenericCommand item) {
		f1.offer(item);
	}
}
