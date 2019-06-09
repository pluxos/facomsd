package br.com.ufu.javaGrpcClientServer.client;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import br.com.ufu.javaGrpcClientServer.CrudServiceGrpc;
import br.com.ufu.javaGrpcClientServer.DeleteRequest;
import br.com.ufu.javaGrpcClientServer.InsertRequest;
import br.com.ufu.javaGrpcClientServer.SelectRequest;
import br.com.ufu.javaGrpcClientServer.UpdateRequest;

public class MenuThread implements Runnable {
	private CrudServiceGrpc.CrudServiceBlockingStub stub;
	private BlockingQueue<Object> responseQueue;
	private static Scanner scn;
	private Object output;
	
	public MenuThread(CrudServiceGrpc.CrudServiceBlockingStub _stub, BlockingQueue<Object> _responseQueue) {
		this.stub = _stub;
		this.responseQueue = _responseQueue;
	}

	public void run() {
		scn = new Scanner(System.in);
		System.out.println("\n----------- Lista de Comandos ------------");
		System.out.println("1. Insert <value>");
		System.out.println("2. Select <id>");
		System.out.println("3. Update <id> <value>");
		System.out.println("4. Delete <id>");
		System.out.println("5. Exit");
		System.out.println("------------------------------------------");
		
		while (true) {
			String input = null;
			
			if (!scn.hasNext())
				input = "Exit";
			else
				input = scn.nextLine();
			
			if (input.toLowerCase().equals("exit"))
				break;
			else {
				output = validateCommand(input);
				
				if (output != null)
					 responseQueue.add(output);
				else
					System.out.println("Resposta: Comando inválido!\n");
			}
		}
		scn.close();
	}

	public Object validateCommand(String text) {
		text = text.trim();
		
		if(text.indexOf(' ') != -1) {
			String command = text.substring(0, text.indexOf(' '));
			String content = text.substring(text.indexOf(' ') + 1).trim();

			if (content.length() == 0)
				return null;

			switch (command.toLowerCase()) {
				case "insert":
					return stub.insert(InsertRequest.newBuilder().setContent(content).build());
					
				case "select":
					if (content.compareTo("*") != 0) {
						try {
							return stub.select(SelectRequest.newBuilder().setId(Long.parseLong(content)).build());
						} catch (NumberFormatException e) {
							return null;
						}
					} else {
						return stub.select(SelectRequest.newBuilder().setAll(true).build());
					}
					
				case "delete":
					try {
						return stub.delete(DeleteRequest.newBuilder().setId(Long.parseLong(content)).build());
					} catch (NumberFormatException e) {
						return null;
					}
					
				case "update":
					if(content.indexOf(' ') != -1) {
						try {
							Long id = Long.parseLong(content.substring(0, content.indexOf(' ')));
							content = content.substring(content.indexOf(' ') + 1).trim();
		
							if (content.length() == 0)
								return null;
				
							return stub.update(UpdateRequest.newBuilder().setId(id).setContent(content).build());
						} catch (NumberFormatException e) {
							return null;
						}
					}
					break;
			}
		}
		return null;
	}
}