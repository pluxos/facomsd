package server.commons.Rows;

import java.util.concurrent.LinkedBlockingQueue;

import server.commons.domain.GenericCommand;

public class RowF3 {
	private static volatile LinkedBlockingQueue<GenericCommand> f3 = new LinkedBlockingQueue<GenericCommand>();
	
	public static LinkedBlockingQueue<GenericCommand> getFifo() {
		return f3;
	}
	
	public static void addItem(GenericCommand item) {
		f3.offer(item);
	}
}
