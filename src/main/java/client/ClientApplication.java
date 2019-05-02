package client;

import client.controller.Client;

public class ClientApplication {
	
	public static void main(String[] args) {
		Thread t = new Thread(new Client(args));
		t.start();
	}
}