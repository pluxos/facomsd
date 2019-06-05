package client;

import client.controller.Client;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.Executors;

public class ClientApplication {
	
	public static void main(String[] args) {
		Thread t = new Thread(new Client(
				args,
				ManagedChannelBuilder.forAddress(
						args[0], Integer.parseInt(args[1])
				).executor(Executors.newFixedThreadPool(1)).usePlaintext().build()
		));
		t.start();
	}
}