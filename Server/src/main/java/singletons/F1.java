package singletons;

import server.ItemFila;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class F1 {

	private static final  BlockingQueue<ItemFila> f1 = new LinkedBlockingQueue<>();
	private static boolean free = false;

	protected F1() {
	}


	public static BlockingQueue<ItemFila> getInstance() {
		return f1;
	}

	public static boolean getFree() {
		return free;
	}

	public static void setFree() {
		free = true;
	}
}