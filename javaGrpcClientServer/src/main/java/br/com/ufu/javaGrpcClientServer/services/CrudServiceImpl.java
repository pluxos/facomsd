package br.com.ufu.javaGrpcClientServer.services;

import br.com.ufu.javaGrpcClientServer.CrudServiceGrpc.CrudServiceImplBase;

import io.grpc.stub.StreamObserver;

import br.com.ufu.javaGrpcClientServer.InsertRequest;
import br.com.ufu.javaGrpcClientServer.InsertResponse;
import br.com.ufu.javaGrpcClientServer.SelectRequest;
import br.com.ufu.javaGrpcClientServer.SelectResponse;
import br.com.ufu.javaGrpcClientServer.UpdateRequest;
import br.com.ufu.javaGrpcClientServer.UpdateResponse;
import br.com.ufu.javaGrpcClientServer.DeleteRequest;
import br.com.ufu.javaGrpcClientServer.DeleteResponse;

import br.com.ufu.javaGrpcClientServer.resources.Input;
import br.com.ufu.javaGrpcClientServer.resources.EventSource;
import br.com.ufu.javaGrpcClientServer.resources.EventSource.Observer;

import java.util.concurrent.BlockingQueue;

public class CrudServiceImpl extends CrudServiceImplBase {	
	public BlockingQueue<Input> requisitionsQueue;
			
	public CrudServiceImpl(BlockingQueue<Input> _requisitions) {
		this.requisitionsQueue = _requisitions;
	}
	
	@Override
	public void insert(
			InsertRequest request, 
			final StreamObserver<InsertResponse> responseObserver) {
		
		EventSource eventSource = new EventSource();

        eventSource.addObserver(new Observer() {
			@Override
			public void update(final String event) {				
				InsertResponse response = InsertResponse.newBuilder().
						setResponse(event).build();
				
				responseObserver.onNext(response);
				responseObserver.onCompleted();
			}
		});
        
        Input input = new Input(-1, request.getContent(), 0, eventSource);
		
		requisitionsQueue.add(input);
	}
	
	@Override 
	public void select(
			SelectRequest request,
			final StreamObserver<SelectResponse> responseObserver) {
		
		EventSource eventSource = new EventSource();

        eventSource.addObserver(new Observer() {
			@Override
			public void update(final String event) {					
				SelectResponse response = SelectResponse.newBuilder().
						setResponse(event).build();

				responseObserver.onNext(response);
				responseObserver.onCompleted();
			}
		});
        
        Input input;
        
        if (request.getAll()) {
        	input = new Input(-1, "*", 1, eventSource);
        } else {
        	input = new Input(request.getId(), "", 1, eventSource);
        }
		
		requisitionsQueue.add(input);
	}	 
	
	@Override
	public void update(
			UpdateRequest request, 
			final StreamObserver<UpdateResponse> responseObserver) {
		
		EventSource eventSource = new EventSource();

        eventSource.addObserver(new Observer() {
			@Override
			public void update(final String event) {				
				UpdateResponse response = UpdateResponse.newBuilder().
						setResponse(event).build();

				responseObserver.onNext(response);
				responseObserver.onCompleted();
			}
		});
        
        Input input = new Input(request.getId(), request.getContent(), 2, eventSource);
		
		requisitionsQueue.add(input);
	}
	
	@Override 
	public void delete(
			DeleteRequest request,
			final StreamObserver<DeleteResponse> responseObserver) {
		
		EventSource eventSource = new EventSource();

        eventSource.addObserver(new Observer() {
			@Override
			public void update(final String event) {					
				DeleteResponse response = DeleteResponse.newBuilder().
						setResponse(event).build();

				responseObserver.onNext(response);
				responseObserver.onCompleted();
			}
		});
        
        Input input = new Input(request.getId(), "", 3, eventSource);
		
		requisitionsQueue.add(input);
	}	 
}