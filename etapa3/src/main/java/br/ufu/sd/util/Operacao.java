package br.ufu.sd.util;

import java.io.Serializable;
import java.math.BigInteger;

public class Operacao implements Serializable{

	private static final long serialVersionUID = 1L;
	private Comando comando;
	private BigInteger chave;
	private byte[] valor;

	public Operacao(Comando comando) {
		this.comando = comando;
	}

	public Comando getComando() {
		return comando;
	}

	public BigInteger getChave() {
		return chave;
	}

	public byte[] getValor() {
		return valor;
	}

	public void setChave(BigInteger chave) {
		this.chave = chave;
	}
	
	public void setValor(byte[] valor) {
		this.valor = valor;
	}

}
