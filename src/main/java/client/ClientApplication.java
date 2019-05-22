package client;

import client.controller.Client;
import io.grpc.ManagedChannelBuilder;

public class ClientApplication {
	
	public static void main(String[] args) {
		Thread t = new Thread(new Client(
				args, ManagedChannelBuilder.forAddress("localhost", 12345).usePlaintext().build()
		));
		t.start();
	}
}