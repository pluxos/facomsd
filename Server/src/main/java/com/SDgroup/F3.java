package com.SDgroup;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class F3 {
 
	private static final  BlockingQueue<ItemFila> f3 = new LinkedBlockingQueue<>();
 
	protected F3() {
	}
 

	public static BlockingQueue<ItemFila> getInstance() {
		return f3;
	}
}