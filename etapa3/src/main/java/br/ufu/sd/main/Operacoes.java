package br.ufu.sd.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

import br.ufu.sd.util.Comando;
import br.ufu.sd.util.Operacao;
import br.ufu.sd.util.OperacaoReply;

public abstract class Operacoes {
	public static OperacaoReply testCREATE(ObjectOutputStream output, ObjectInputStream input, BigInteger chave,
			byte[] valor) throws IOException, ClassNotFoundException {
		Operacao operacao = new Operacao(Comando.CREATE);
		operacao.setChave(chave);
		operacao.setValor(valor);
		output.writeObject(operacao);
		return (OperacaoReply) input.readObject();
	}

	public static OperacaoReply testREAD(ObjectOutputStream output, ObjectInputStream input, BigInteger chave)
			throws IOException, ClassNotFoundException {
		Operacao operacao = new Operacao(Comando.READ);
		operacao.setChave(chave);
		output.writeObject(operacao);
		return (OperacaoReply) input.readObject();
	}

	public static OperacaoReply testUPDATE(ObjectOutputStream output, ObjectInputStream input, BigInteger chave,
			byte[] valor) throws IOException, ClassNotFoundException {
		Operacao operacao = new Operacao(Comando.UPDATE);
		operacao.setChave(chave);
		operacao.setValor(valor);
		output.writeObject(operacao);
		return (OperacaoReply) input.readObject();
	}

	public static OperacaoReply testDELETE(ObjectOutputStream output, ObjectInputStream input, BigInteger chave)
			throws IOException, ClassNotFoundException {
		Operacao operacao = new Operacao(Comando.DELETE);
		operacao.setChave(chave);
		output.writeObject(operacao);
		return (OperacaoReply) input.readObject();
	}
}
