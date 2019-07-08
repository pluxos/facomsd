package state_machine.server;

import state_machine.type.Item;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class F3 {

	private static final  BlockingQueue<Item> f3 = new LinkedBlockingQueue<>();

	protected F3() {
	}


	public static BlockingQueue<Item> getInstance() {
		return f3;
	}
}