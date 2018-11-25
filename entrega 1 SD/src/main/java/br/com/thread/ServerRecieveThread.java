package br.com.thread;

import br.com.enums.Operation;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRecieveThread implements Runnable {

	private ServerSocket serverSocket;
	
	private Queue< String > executeQueue;
	
	private ExecutorService executor = Executors.newCachedThreadPool();


	public ServerRecieveThread( ServerSocket serverSocket, Queue< String > executeQueue ) {
		this.serverSocket = serverSocket;
		this.executeQueue = executeQueue;
	}

	@Override
	public void run() {


		while ( true ) {
			Socket connectionSocket = null;
			try {
				connectionSocket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {



				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int ch;
				try {
					while (((ch = connectionSocket.getInputStream().read()) != '\n')) {
						if (ch == -1)
							break;
						baos.write(ch);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				String sentence = baos.toString();


				System.out.println( "Mensagem recebida: " + sentence );

				/**
				 * Adiciona a mensagem na fila do log.
				 */
				messageToQueue( sentence, connectionSocket.getInetAddress(), connectionSocket.getPort() );

				/**
				 * Envia a confirmacao de recebimento para o cliente.
				 */
				InetAddress IPAddress = connectionSocket.getInetAddress();
				int port = connectionSocket.getPort();
				String sendBack = "Executado o comando " + sentence;

				DataOutputStream outToClient = new DataOutputStream(
						connectionSocket.getOutputStream());
				outToClient.writeBytes(sentence + '\n');

				Thread.sleep( 1 );

			} catch ( Exception ex ) {
				ex.printStackTrace();
			}

		}

	}

	/**
	 * Trata a mensagem e adiciona na fila de log
	 * 
	 * @param message
	 */
	private void messageToQueue( String message, InetAddress recieveAddress, int recievePort ) {

		List< String > list = new LinkedList< String >( Arrays.asList( message.split( " " ) ) );
		String operation = list.get( 0 );

		if ( Operation.RETURN.name().equals( operation ) ) {
			if( list.size() < 2 ) {
				executeQueue.add( operation + ";" + recieveAddress.toString() + ";" + String.valueOf( recievePort ) );
			} else {
				executeQueue.add( operation + ";" + list.get( 1 ) + ";" + recieveAddress.toString() + ";" + String.valueOf( recievePort ) );
			}
		} else {

			list.remove( 0 );
			String header = list.get( 0 );
			list.remove( 0 );

			if ( Operation.DELETE.name().equals( operation ) ) {
				executeQueue.add( operation.toUpperCase() + ";" + header );
			} else {
				String log = "";
				for ( String current : list ) {
					log = log.concat( current + " " );
				}
				executeQueue.add( operation.toUpperCase() + ";" + header + ";" + log.substring( 0, log.length() - 1 ) );
			}
		}

	}

}
