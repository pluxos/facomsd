package br.ufu.sd.util;

import java.io.Serializable;

public class OperacaoReply implements Serializable{
	private static final long serialVersionUID = 1L;
	private Comando comando;
	private StatusResponse status;
	private byte[] valor;
	
	public OperacaoReply(Comando comando, StatusResponse status) {
		this.comando = comando;
		this.status = status;
	}
	
	public StatusResponse getStatus() {
		return status;
	}
	
	public Comando getComando() {
		return comando;
	}
	public byte[] getValor() {
		return valor;
	}
	
	public void setValor(byte[] valor) {
		this.valor = valor;
	}
}
