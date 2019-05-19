package server.commons.Rows;

import java.util.concurrent.LinkedBlockingQueue;

import server.commons.domain.GenericCommand;

public class RowF1 {
    private volatile static LinkedBlockingQueue<GenericCommand> f1 = new LinkedBlockingQueue<>();

    public static LinkedBlockingQueue<GenericCommand> getFifo() {
    	return f1;
    }
    
    public synchronized static void addItem(GenericCommand item) {
        f1.offer(item);
    }
}
