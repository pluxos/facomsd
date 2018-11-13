package br.com.jvitoraa;

import java.io.IOException;

import br.com.jvitoraa.client.GrpcClient;

public class ClientApplication {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		GrpcClient client = new GrpcClient();
		client.start();
	}

}
