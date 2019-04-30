// package com.SDgroup;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class F2 {
 
	private static final  BlockingQueue<ItemFila> f2 = new LinkedBlockingQueue<>();
 
	protected F2() {
	}
 

	public static BlockingQueue<ItemFila> getInstance() {
		return f2;
	}
}