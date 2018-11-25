package br.com.thread;

import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import br.com.context.Context;
import br.com.enums.Operation;
import br.com.proto.ContextProto.SubscribeResponse;
import io.grpc.stub.StreamObserver;

public class ExecutorThread implements Runnable {

	private Queue< String > logQueue;

	private Queue< String > executeQueue;

	private Context context;

	private ServerSocket serverSocket;
	
	private Map< String, List< StreamObserver< SubscribeResponse > > > observers;
	
	private Boolean semaphore;
	
	public ExecutorThread(
		ServerSocket serverSocket,
		Queue< String > logQueue,
		Queue< String > executeQueue,
		Context context,
		Map< String, List< StreamObserver< SubscribeResponse > > > observers,
		Boolean semaphore ) {
		this.logQueue = logQueue;
		this.executeQueue = executeQueue;
		this.context = context;
		this.serverSocket = serverSocket;
		this.observers = observers;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {

		while ( true ) {
			try {
				
				String instruction = executeQueue.poll();
				if ( instruction != null && !semaphore ) {
					System.out.println( "Executando instrucao: " + instruction );
					execute( instruction );
				}
				Thread.sleep( 1 );
			} catch ( Exception ex ) {
				ex.printStackTrace();
			}
		}

	}

	/**
	 * Executa a instrucao
	 * @param instruction
	 */
	private void execute( String instruction ) {
		List< String > params = Arrays.asList( instruction.split( ";" ) );
		if ( Operation.RETURN.name().equals( params.get( 0 ) ) ) {
			sendDatagram( params );
			return;
		} else if ( Operation.INSERT.name().equals( params.get( 0 ) ) ) {
			context.put( new BigInteger( params.get( 1 ) ), params.get( 2 ) );
			alertSubscribers( params.get( 1 ), "INSERT -> " + params.get( 2 ) );
		} else if ( Operation.UPDATE.name().equals( params.get( 0 ) ) ) {
			if( !"Chave nao encontrada no contexto.".equals( context.get( new BigInteger( params.get( 1 ) ) ) ) ) {
				context.put( new BigInteger( params.get( 1 ) ), params.get( 2 ) );
				alertSubscribers( params.get( 1 ), "UPDATE -> " + params.get( 2 ) );
			}
		} else {
			context.remove( new BigInteger( params.get( 1 ) ) );
			alertSubscribers( params.get( 1 ), "DELETE -> " + params.get( 1 ) );
		}

		logQueue.add( instruction );

	}

	/**
	 * Retorna para o client o datagram
	 * @param params
	 */
	private void sendDatagram( List< String > params ) {
		try {
			System.out.println( "Enviando o contexto para o remetente" );

			if( params.size() < 4 ) {
				/*sendData = context.stringfy().getBytes();
				DatagramPacket sendPacket = new DatagramPacket( sendData, sendData.length,
					InetAddress.getByName( params.get( 1 ).replace( "/", "" ) ), Integer.parseInt( params.get( 2 ) ) );
				serverSocket.send( sendPacket );*/
			} else {
				/*sendData = context.get( new BigInteger( params.get( 1 ) ) ).getBytes();
				DatagramPacket sendPacket = new DatagramPacket( sendData, sendData.length,
					InetAddress.getByName( params.get( 2 ).replace( "/", "" ) ), Integer.parseInt( params.get( 3 ) ) );
				serverSocket.send( sendPacket );*/
			}
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	private void alertSubscribers( String key, String changed ) {
	
		try {
			if( observers.get( key ) != null ) {
				List< StreamObserver< SubscribeResponse > > observerList = observers.get( key );
				for( StreamObserver< SubscribeResponse > observer : observerList ) {
					SubscribeResponse response = SubscribeResponse.newBuilder().setMessage( "Chave " + key + " alterada pela instrucao " + changed ).build();
					observer.onNext( response );
				}
			}
		} catch( Exception ex ) {
			// do nothing
		}
		
	}

}
