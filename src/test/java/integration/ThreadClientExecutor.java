package integration;

import java.io.IOException;

import client.ClientApplication;

public class ThreadClientExecutor implements Runnable {

	@Override
	public void run() {
		System.out.println("hello");
		try {
			ClientApplication.main(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}