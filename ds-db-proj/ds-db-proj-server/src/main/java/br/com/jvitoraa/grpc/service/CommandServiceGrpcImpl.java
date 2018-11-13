package br.com.jvitoraa.grpc.service;

import com.ufu.jvitoraa.interaction.CommandServiceGrpc.CommandServiceImplBase;
import com.ufu.jvitoraa.interaction.CreateCommand;
import com.ufu.jvitoraa.interaction.DeleteCommand;
import com.ufu.jvitoraa.interaction.ReadCommand;
import com.ufu.jvitoraa.interaction.Response;
import com.ufu.jvitoraa.interaction.UpdateCommand;

import br.com.jvitoraa.grpc.dto.CommandDto;
import br.com.jvitoraa.queue.controller.QueueController;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandServiceGrpcImpl extends CommandServiceImplBase {

	private QueueController queueController;
	
	@Override
	public void create(CreateCommand request, StreamObserver<Response> responseObserver) {
		
		this.queueController.receiveCommand(
				new CommandDto("CREATE", request.getId(), request.getValue(), responseObserver));

	}

	@Override
	public void update(UpdateCommand request, StreamObserver<Response> responseObserver) {

		this.queueController.receiveCommand(
				new CommandDto("UPDATE", request.getId(), request.getValue(), responseObserver));
		
	}

	@Override
	public void delete(DeleteCommand request, StreamObserver<Response> responseObserver) {

		this.queueController.receiveCommand(
				new CommandDto("DELETE", request.getDeleteVal(), responseObserver));
		
	}

	@Override
	public void read(ReadCommand request, StreamObserver<Response> responseObserver) {

		this.queueController.receiveCommand(
				new CommandDto("READ", request.getReadVal(), responseObserver));
		
	}

}
