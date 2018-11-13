package br.com.jvitoraa;

import java.io.IOException;

import br.com.jvitoraa.server.GrpcServer;

public class ServerApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		GrpcServer server = new GrpcServer();
		server.start();
	}

}
