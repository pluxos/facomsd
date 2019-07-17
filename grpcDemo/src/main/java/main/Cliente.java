package main;

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.example.grpc.CRUDGrpc;
import com.example.grpc.Comando;
import com.example.grpc.Result;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class Cliente {
	
	private final ManagedChannel channel;
	private static CRUDGrpc.CRUDBlockingStub blockingStub = null;
	
	public Cliente(String host, int port) {
	    this(ManagedChannelBuilder.forAddress(host, port)
	        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
	        // needing certificates.
	        .usePlaintext()
	        .build());
	  }
	
	/** Construct client for accessing HelloWorld server using the existing channel. */
	Cliente(ManagedChannel channel) {
	    this.channel = channel;
	    blockingStub = CRUDGrpc.newBlockingStub(channel);
	  }
	
	public void shutdown() throws InterruptedException {
	    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	  }
	
	public static void sendCreate(long chave, String valor) {
		Comando c = Comando.newBuilder().setChave(chave).setValor(valor).build();
		Result r;
		
		try {
			r = blockingStub.create(c);
		}catch (StatusRuntimeException e) {
			System.out.println("RPC failed: {0}" + e.getStatus());
			return;
		}
		
		System.out.println(r.getMessage());
	}
	
	public static void sendRead(long chave) {
		Comando c = Comando.newBuilder().setChave(chave).build();
		Result r;
		
		try {
			r = blockingStub.read(c);
		}catch (StatusRuntimeException e) {
			System.out.println("RPC failed: {0}" + e.getStatus());
			return;
		}
		
		System.out.println(r.getMessage());
	}
	
	public static void sendUpdate(long chave, String valor) {
		Comando c = Comando.newBuilder().setChave(chave).setValor(valor).build();
		Result r;
		
		try {
			r = blockingStub.update(c);
		}catch (StatusRuntimeException e) {
			System.out.println("RPC failed: {0}" + e.getStatus());
			return;
		}
		
		System.out.println(r.getMessage());
	}
	
	public static void sendDelete(long chave, String valor) {
		Comando c = Comando.newBuilder().setChave(chave).setValor(valor).build();
		Result r;
		
		try {
			r = blockingStub.delete(c);
		}catch (StatusRuntimeException e) {
			System.out.println("RPC failed: {0}" + e.getStatus());
			return;
		}
		
		System.out.println(r.getMessage());
	}
	
	public static void main(String[] args) throws Exception {
		String host = args[0];
		int port = Integer.parseInt(args[1]);
	    Cliente client = new Cliente(host, port);
	    String input;
	    Scanner scanIn = new Scanner(System.in);
	    BigInteger chave;
	    String valor = null;
	    long key;
	    
	    try {
	    	while (true){
	    		
			
				System.out.println("C - Create");
				System.out.println("R - Read");
				System.out.println("U - Update");
				System.out.println("D - Delete");
				System.out.println("X - eXit");
				
				
				input = scanIn.nextLine();
				
			
				try{
					switch (input.toUpperCase()){
						case "C":

									System.out.println("Create\n");
									Scanner create = new Scanner(System.in);

									System.out.print("Chave: ");
									chave = create.nextBigInteger();
									System.out.print("Valor: ");
									valor = create.next();

									if(valor.length() > 100){
										System.out.println("Valor ultrapaça tamanho máximo permitido");
										System.out.println("Operação não realizada");
										break;
									}
									key = chave.longValue();
									sendCreate(key, valor);
									break;
						case "R":	
									System.out.println("Read\n");
									Scanner read = new Scanner(System.in);

									System.out.print("Chave: ");
									chave = read.nextBigInteger();

									key = chave.longValue();
									sendRead(key);
									break;
						case "U":
									System.out.println("Update\n");
									Scanner update = new Scanner(System.in);

									System.out.print("Chave: ");
									chave = update.nextBigInteger();
									
									System.out.print("Novo valor: ");
									valor = update.next();

									if(valor.length() > 100){
										System.out.println("Valor ultrapaça tamanho máximo permitido");
										System.out.println("Operação não realizada");
										break;
									}

									key = chave.longValue();
									sendUpdate(key, valor);
									break;
						case "D":
									System.out.println("Delete\n");
									Scanner delete = new Scanner(System.in);

									System.out.print("Chave: ");
									chave = delete.nextBigInteger();

									key = chave.longValue();
									sendDelete(key, valor);
									break;
						case "X": 
									scanIn.close();
									System.out.println("Saindo...");
									return ;
						default: 
									System.out.println("opcao inválida"); break;
						
					}
				}catch (InputMismatchException e){
					System.out.println("Formato de chave inválido");
					System.out.println("Operação não realizada");
				} // tratamento de chave inválida
				  // continua dentro do loop
			}
	    } finally {
	      client.shutdown();
	    }
	  }

}
