package br.com.ufu.ClientServer.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

import io.atomix.core.map.DistributedMap;

public class ExecutionThread implements Runnable {
	private final BlockingQueue<Input> queue;
	static private DistributedMap<BigInteger, byte[]> dataBase;
	long nextKey = 0;

	public ExecutionThread(DistributedMap<BigInteger, byte[]> _dataBase, BlockingQueue<Input> _queue) {
		this.dataBase = _dataBase;
		this.queue = _queue;
	}

	public void run() {
		try {
			while (true) {
				Input input = queue.take();
				String[] cmdArgs = input.getCommand().split(" ");
				String cmd = cmdArgs[0].toLowerCase();
				String response = "\n";

				if (cmd.compareTo("insert") == 0) {
					String arg = cmdArgs[1];
					BigInteger key = BigInteger.valueOf(nextKey++);
					dataBase.put(key, arg.getBytes());
					response += arg + " inserido com sucesso.\n";
				} else if (cmd.compareTo("select") == 0) {
					String arg0 = cmdArgs[1];
					if (arg0.compareTo("*") == 0) {
						for (Map.Entry<BigInteger, byte[]> pair : dataBase.entrySet()) {
							response += pair.getKey().toString() + " " + new String(pair.getValue()) + "\n";
						}
					} else {
						BigInteger key = BigInteger.valueOf(Long.parseLong(arg0));
						if (dataBase.containsKey(key)) {
							response += key + " " + new String(dataBase.get(key)) + "\n";
						} else {
							response += "Não existe valores para o id: " + key.toString() + "\n";
						}
					}
				} else if (cmd.compareTo("update") == 0) {
					String arg0 = cmdArgs[1];
					String arg1 = cmdArgs[2];

					BigInteger key = BigInteger.valueOf(Long.parseLong(arg0));
					if (dataBase.containsKey(key)) {
						dataBase.replace(key, arg1.getBytes());
						response += "Item de id " + key.toString() + " atualizado\n";
					} else {
						response += "Não foi possivel encontrar o item de id: " + key.toString() + "\n";
					}
				} else if (cmd.compareTo("delete") == 0) {
					String arg0 = cmdArgs[1];
					
					BigInteger key = BigInteger.valueOf(Long.parseLong(arg0));
					if (dataBase.containsKey(key)) {
						dataBase.remove(key);
						response += "Item de id " + key.toString() + " removido.\n";
					} else {
						response += "Não foi possivel encontrar o item de id: " + key.toString() + "\n";
					}
				}

				System.out.println("Comando executado: " + cmd);

				ObjectOutputStream outputStream = input.getOutputStream();

				if (outputStream != null) {
					outputStream.flush();
					outputStream.writeObject(response);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
