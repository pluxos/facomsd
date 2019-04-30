package com.SDgroup;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class F1 {
 
	private static final  BlockingQueue<ItemFila> f1 = new LinkedBlockingQueue<>();
 
	protected F1() {
	}
 

	public static BlockingQueue<ItemFila> getInstance() {
		return f1;
	}
}