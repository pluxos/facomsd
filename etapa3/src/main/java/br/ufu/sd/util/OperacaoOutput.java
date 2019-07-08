package br.ufu.sd.util;

import java.io.ObjectOutputStream;

public class OperacaoOutput {

	private Operacao operacao;
	private ObjectOutputStream output;
	
	public OperacaoOutput(Operacao operacao, ObjectOutputStream output) {
		this.operacao = operacao;
		this.output = output;
	}
	
	public Operacao getOperacao() {
		return operacao;
	}
	
	public ObjectOutputStream getOutput() {
		return output;
	}

}
