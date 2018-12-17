package br.com.jvitoraa.grpc.facade;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.ufu.jvitoraa.interaction.CommandServiceGrpc;
import com.ufu.jvitoraa.interaction.CreateCommand;
import com.ufu.jvitoraa.interaction.DeleteCommand;
import com.ufu.jvitoraa.interaction.ReadCommand;
import com.ufu.jvitoraa.interaction.UpdateCommand;

import br.com.jvitoraa.observer.GrpcObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Getter;

public class ClientFacade {
	
	private static final Logger LOGGER = Logger.getLogger(ClientFacade.class.getName());
	
	private CommandServiceGrpc.CommandServiceStub stub;
	@Getter
	private ManagedChannel channel;
	
	public ClientFacade(Integer port) {
		
		LOGGER.info("Client connecting to Server on port: " + port);
		channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
		stub = CommandServiceGrpc.newStub(channel);
		
	}
	
	
	public void create(Long id, String value, GrpcObserver observer) {
		
		LOGGER.info("Creating data with id: " + id);
		stub.create(CreateCommand.newBuilder().setId(id).setValue(value).build(), observer);
		
	}
	
	public void update(Long id, String value, GrpcObserver observer) {
		
		LOGGER.info("Updating data with id: " + id);
		stub.update(UpdateCommand.newBuilder().setId(id).setValue(value).build(), observer);
		
	}
	
	public void read(Long id, GrpcObserver observer) {
		
		LOGGER.info("Reading data with id: " + id);
		stub.read(ReadCommand.newBuilder().setReadVal(id).build(), observer);
		
	}
	
	public void delete(Long id, GrpcObserver observer) {
		
		LOGGER.info("Deleting data with id: " + id);
		stub.delete(DeleteCommand.newBuilder().setDeleteVal(id).build(), observer);
		
	}
	
	public void stop() throws InterruptedException {
		channel.shutdown().awaitTermination(5000, TimeUnit.MILLISECONDS);
	}
}
