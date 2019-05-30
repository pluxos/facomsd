package model;

import java.net.Socket;
import java.math.BigInteger;

public class ItemFila {
	private Socket socket;
	private byte[] controll;
	private byte[] key;
	private byte[] value;

	private boolean ourResponsability = true;

	public ItemFila(Socket socket, byte[] controll, byte[] key, byte[] value) {
		this.socket = socket;
		this.controll = controll;
		this.key = key;
		this.value = value;
	}

	public void setOurResponsability(boolean b){
		this.ourResponsability = b;
	}

	public boolean getOurResponsability(){
		return ourResponsability;
	}

	public ItemFila(Socket socket, byte[] controll, byte[] key) {
		this.socket = socket;
		this.controll = controll;
		this.key = key;
	}

	public void print() {
		String x = new String(controll);
		BigInteger y = new BigInteger(key);
		if (value != null) {
			String z = new String(value);
			System.out.println(x + " " + y + " " + z);
		} else {
			System.out.println(x + " " + y);
		}
	}

	@Override
	public String toString() {
		String x = new String(controll);
		BigInteger y = new BigInteger(key);
		if (value != null) {
			String z = new String(value);
			return (x + " " + y + " " + z);
		} else {
			return (x + " " + y);
		}

	}

	public Socket getSocket() {
		return socket;
	}

	public byte[] getControll() {
		return controll;
	}

	public byte[] getKey() {
		return key;
	}

	public byte[] getValue() {
		return value;
	}
}