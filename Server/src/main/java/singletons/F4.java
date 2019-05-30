package singletons;

import model.ItemFila;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class F4 {

	private static final  BlockingQueue<ItemFila> f4 = new LinkedBlockingQueue<>();
	private static boolean free = false;
	protected F4() {
	}


	public static BlockingQueue<ItemFila> getInstance() {
		return f4;
	}

	public static boolean getFree() {
		return free;
	}

	public static void setFree() {
		free = true;
	}

	public static void setBlocked() {
		free = false;
	}

}