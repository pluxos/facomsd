package br.com.jvitoraa.grpc.facade;

import java.io.IOException;

import br.com.jvitoraa.grpc.service.CommandServiceGrpcImpl;
import br.com.jvitoraa.queue.controller.QueueController;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.Data;

@Data
public class ServerFacade {
	
	private Server server;
	
	public ServerFacade(Integer port, QueueController queueController) {
		server = ServerBuilder.forPort(port).addService(new CommandServiceGrpcImpl(queueController)).build();
	}
	
	public void startUp() throws IOException {
		server.start();
	}
	
	public void awaitTermination() throws InterruptedException {
		server.awaitTermination();
	}
	
	public void shutDown() {
		server.shutdown();
	}

}
