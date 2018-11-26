package br.com.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import br.com.context.Context;
import br.com.enums.Operation;
import br.com.proto.ContextProto.ContextRequest;
import br.com.proto.ContextProto.ContextResponse;
import br.com.proto.ContextProto.SubscribeRequest;
import br.com.proto.ContextProto.SubscribeResponse;
import br.com.proto.ContextServiceGrpc;
import io.grpc.stub.StreamObserver;

public class ContextService extends ContextServiceGrpc.ContextServiceImplBase {

	private Queue< String > logQueue;

	private Queue< String > Queue;

	private Context context;

	private Map< String, List< StreamObserver< SubscribeResponse > > > observers;

	public ContextService( Queue< String > logQueue, Queue< String > Queue, Context context, Map< String, List< StreamObserver< SubscribeResponse > > > observers ) {
		super();
		this.logQueue = logQueue;
		this.Queue = Queue;
		this.context = context;
		this.observers = observers;
	}

	@Override
	public void insert( ContextRequest request, StreamObserver< ContextResponse > responseObserver ) {
		messageToQueue( request.getInstruction() );
		ContextResponse response = ContextResponse.newBuilder().setMessage( "Instrucao enviada para execucao " + request.getInstruction() ).build();
		responseObserver.onNext( response );
		responseObserver.onCompleted();
	}

	@Override
	public void delete( ContextRequest request, StreamObserver< ContextResponse > responseObserver ) {
		messageToQueue( request.getInstruction() );
		ContextResponse response = ContextResponse.newBuilder().setMessage( "Instrucao enviada para execucao " + request.getInstruction() ).build();
		responseObserver.onNext( response );
		responseObserver.onCompleted();
	}

	@Override
	public void update( ContextRequest request, StreamObserver< ContextResponse > responseObserver ) {
		messageToQueue( request.getInstruction() );
		ContextResponse response = ContextResponse.newBuilder().setMessage( "Instrucao enviada para execucao " + request.getInstruction() ).build();
		responseObserver.onNext( response );
		responseObserver.onCompleted();
	}

	@Override
	public void find( ContextRequest request, StreamObserver< ContextResponse > responseObserver ) {
		String instruction = request.getInstruction();
		List< String > params = Arrays.asList( instruction.split( " " ) );
		String stringify = context.get( new BigInteger( params.get( 1 ) ));
		ContextResponse response = ContextResponse.newBuilder().setMessage( stringify ).build();
		responseObserver.onNext( response );
		responseObserver.onCompleted();
	}

	@Override
	public void subscribe( SubscribeRequest request, StreamObserver< SubscribeResponse > responseObserver ) {
		List< StreamObserver< SubscribeResponse > > registry = observers.get( request.getKey() );
		if( registry != null ) {
			registry.add( responseObserver );
			observers.put( request.getKey(), registry );
		} else {
			List< StreamObserver< SubscribeResponse > > list = new ArrayList< StreamObserver< SubscribeResponse > >();
			list.add( responseObserver );
			observers.put( request.getKey(), list );
		}
		SubscribeResponse response = SubscribeResponse.newBuilder().setMessage( "Subscricao realizada com sucesso" ).build();
		responseObserver.onNext( response );
	}

	/**
	 * Trata a mensagem e adiciona na fila de log
	 *
	 * @param message
	 */
	private void messageToQueue( String message ) {

		List< String > list = new LinkedList< String >( Arrays.asList( message.split( " " ) ) );
		String operation = list.get( 0 );

		list.remove( 0 );
		String header = list.get( 0 );
		list.remove( 0 );

		if ( Operation.DELETE.name().equals( operation ) ) {
			Queue.add( operation.toUpperCase() + ";" + header );
		} else {
			String log = "";
			for ( String current : list ) {
				log = log.concat( current + " " );
			}
			Queue.add( operation.toUpperCase() + ";" + header + ";" + log.substring( 0, log.length() - 1 ) );
		}

	}

}
