package server.commons.rows;

import server.commons.domain.GenericCommand;

import java.util.concurrent.LinkedBlockingQueue;

public class RowF4 {
	
	private volatile static LinkedBlockingQueue<GenericCommand> f4 = new LinkedBlockingQueue<>();

	public static LinkedBlockingQueue<GenericCommand> getFifo() {
		return f4;
	}

	public synchronized static void addItem(GenericCommand item) {
		f4.offer(item);
	}
}
