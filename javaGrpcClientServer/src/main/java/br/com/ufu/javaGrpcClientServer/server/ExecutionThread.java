package br.com.ufu.javaGrpcClientServer.server;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import br.com.ufu.javaGrpcClientServer.resources.Input;

public class ExecutionThread implements Runnable {
	private BlockingQueue<Input> executionQueue;
	
	private HashMap<Long, byte[]> dataBase;
	
	private long nextKey;
	private Input input;
	
	public ExecutionThread(BlockingQueue<Input> _executionQueue, HashMap<Long, byte[]> _dataBase) {
		this.executionQueue = _executionQueue;
		this.dataBase = _dataBase;
		nextKey = dataBase.size();
	}
	
	public void run() {
		try {
			while (true) {
				if (executionQueue != null && executionQueue.size() != 0) {
					input = executionQueue.take();
					switch (input.getOperation()) {
					case 0:
						dataBase.put(nextKey++, input.getContent().getBytes());
						if (input.getEventSource() != null) {
							input.getEventSource().reply("OK : ID = " + String.valueOf(nextKey - 1));
						}
						break;
					case 1:
						if (input.getContent().compareTo("*") == 0) {
							String response = "";
							for (HashMap.Entry<Long, byte[]> pair : dataBase.entrySet()) {
								response += "ID: " + pair.getKey().toString() + " Conteudo: " + new String(pair.getValue()) + " | ";
							}
							if (input.getEventSource() != null) {
								input.getEventSource().reply(response);
							}
						} else if (dataBase.containsKey(input.getId())) {
							if (input.getEventSource() != null) {
								input.getEventSource().reply(input.getId() + " " + new String(dataBase.get(input.getId())));
							}
						} else {
							if (input.getEventSource() != null) {
								input.getEventSource().reply("NOK");
							}
						}
						break;
					case 2:
						if (dataBase.containsKey(input.getId())) {
							dataBase.replace(input.getId(), input.getContent().getBytes());
							if (input.getEventSource() != null) {
								input.getEventSource().reply("OK");
							}
						} else {
							if (input.getEventSource() != null) {
								input.getEventSource().reply("NOK");
							}
						}
						break;
					case 3:						
						if (dataBase.containsKey(input.getId())) {
							dataBase.remove(input.getId());
							if (input.getEventSource() != null) {
								input.getEventSource().reply("OK");
							}
						} else {
							if (input.getEventSource() != null) {
								input.getEventSource().reply("NOK");
							}
						}
						break;

					default:
						if (input.getEventSource() != null) {
							input.getEventSource().reply("NOK");
						}
						break;
					}
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Ocorreu uma falha na execuçã das requisições.\n"
					+ "O servidor será finalizado.");
		}
	}
}
