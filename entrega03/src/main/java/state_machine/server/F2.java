package state_machine.server;

import state_machine.type.Item;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class F2 {

	private static final  BlockingQueue<Item> f2 = new LinkedBlockingQueue<>();

	protected F2() {
	}


	public static BlockingQueue<Item> getInstance() {
		return f2;
	}
}