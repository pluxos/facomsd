package com.sd.etapa2.util;

import java.io.Serializable;
import java.math.BigInteger;

public class Operacao implements Serializable {

	private static final long serialVersionUID = 1L;
	private Tipo tipo;
	private Status status;
	private String mensagem;
	private BigInteger chave;
	private String valor;
	private String elemento;
	private Banco banco;
	private Integer serverId;

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public BigInteger getChave() {
		return chave;
	}

	public void setChave(BigInteger chave) {
		this.chave = chave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getValores() {
		return elemento;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void banco() {
		banco = new Banco();
	}

	public String getValor(Integer chave) {
		return banco.getValor(BigInteger.valueOf(chave));
	}
}
