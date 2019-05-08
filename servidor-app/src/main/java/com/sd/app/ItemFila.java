package com.sd.app;

import java.math.BigInteger;
import java.net.Socket;

public class ItemFila {
	Socket socket;
	String operacao;
	BigInteger key;
	String valor;

	public ItemFila(Socket socket, String operacao, BigInteger key, String valor) {
		this.socket = socket;
		this.key = key;
		this.valor = valor;
		this.operacao = operacao;
	}

	public ItemFila(Socket socket, String operacao, BigInteger key) {
		this.socket = socket;
		this.key = key;
		this.operacao = operacao;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BigInteger getKey() {
		return key;
	}

	public void setKey(BigInteger key) {
		this.key = key;
	}

	public String getValor() {
		return valor;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		if (valor == null) {
			return operacao + " " + key;
		}
		return operacao + " " + key + " " + valor;
	}
	
	public String msgErro(String msg) {
		msg += "\n";
		msg += operacao.equals("CREATE") ? "CREATE ERROR: JA EXISTE ESSA KEY(" + key+ ")" : "";
		msg += operacao.equals("READ") ? "READ ERROR: NAO EXISTE ESSA KEY(" + key+ ")" : "";
		msg += operacao.equals("DELETE") ? "DELETE ERROR: NAO EXISTE ESSA KEY(" + key+ ")" : "";
		msg += operacao.equals("UPDATE") ? "UPDATE ERROR: NAO EXISTE ESSA KEY(" + key+ ")" : "";
		return msg;
	}

	public void print() {
		if (valor == null)
			System.out.println(operacao + " " + key);
		else
			System.out.println(operacao + " " + key + " " + valor);
	}

}