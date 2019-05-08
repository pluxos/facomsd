package com.sd.app;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Kv implements Runnable {

	public Map<BigInteger, byte[]> Database;
	protected BlockingQueue<ItemFila> f3;

	public Kv() {
		this.Database = new HashMap<>();
		this.f3 = F3.getInstance();
	}

	public boolean Insert(BigInteger key, byte[] value) {
		if (this.Database.containsKey(key)) {
			return false;
		} else {
			this.Database.put(key, value);
			return true;
		}
	}

	public boolean contemChave(BigInteger key) {
		return this.Database.containsKey(key);
	}

	public byte[] Read(BigInteger key) {
		return this.Database.get(key);
	}

	public Boolean Update(BigInteger key, byte[] value) {
		if (this.Database.containsKey(key)) {
			this.Database.put(key, value);
			return true;
		} else {
			return false;
		}
	}

	public Boolean Delete(BigInteger key) {
		if (this.Database.containsKey(key)) {
			this.Database.remove(key);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void run() {
		try {
			DataOutputStream out = null;
			while (true) {
				ItemFila obj = f3.take();
				String msg = "";
				if(obj.getSocket() != null) {
					out = new DataOutputStream(obj.getSocket().getOutputStream());
				}
			
;				try {
					String hash = "HASH:: ";
					String erroHash = "ERRO DE HASH:: ";
					if (obj.getOperacao().equals("CREATE")) {
						msg = Insert(obj.getKey(), obj.getValor().getBytes()) ? hash+ obj.toString()
								: obj.msgErro(erroHash);
					} else if (obj.getOperacao().equals("UPDATE")) {
						msg = Update(obj.getKey(), obj.getValor().getBytes()) ?  hash + obj.toString()
								: obj.msgErro(erroHash);
					} else if (obj.getOperacao().equals("DELETE")) {
						msg = Delete(obj.key) ?  hash+ obj.toString() : obj.msgErro(erroHash);
					} else {
						msg = contemChave(obj.key) ? hash+ obj.toString() : obj.msgErro(erroHash);					}
					// envia para cliente status da msg
					
					if(obj.socket != null) {// tem que ter valor para enviar para o cliente
						
						out.writeUTF(msg);
					}
						
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
