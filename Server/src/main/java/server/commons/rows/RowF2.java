package server.commons.rows;

import java.util.concurrent.LinkedBlockingQueue;

import server.commons.domain.GenericCommand;

public class RowF2 {
	
	private static volatile LinkedBlockingQueue<GenericCommand> f2 = new LinkedBlockingQueue<>();
	
	public static LinkedBlockingQueue<GenericCommand> getFifo() {
		return f2;
	}
	
	public static void addItem(GenericCommand item) {
		f2.offer(item);
	}
}
