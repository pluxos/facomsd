package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

import util.Operacao;
import util.Tipo;

public abstract class Operacoes {
	public static Operacao testCREATE(ObjectOutputStream output, ObjectInputStream input, BigInteger chave,
			String valor) throws IOException, ClassNotFoundException {
		Operacao operacao = new Operacao();
		operacao.setTipo(Tipo.CREATE);
		operacao.setChave(chave);
		operacao.setValor(valor);
		output.writeObject(operacao);
		return (Operacao) input.readObject();
	}

	public static Operacao testREAD(ObjectOutputStream output, ObjectInputStream input, BigInteger chave)
			throws IOException, ClassNotFoundException {
		Operacao operacao = new Operacao();
		operacao.setTipo(Tipo.READ);
		operacao.setChave(chave);
		output.writeObject(operacao);
		return (Operacao) input.readObject();
	}

	public static Operacao testUPDATE(ObjectOutputStream output, ObjectInputStream input, BigInteger chave,
			String valor) throws IOException, ClassNotFoundException {
		Operacao operacao = new Operacao();
		operacao.setTipo(Tipo.UPDATE);
		operacao.setChave(chave);
		operacao.setValor(valor);
		output.writeObject(operacao);
		return (Operacao) input.readObject();
	}

	public static Operacao testDELETE(ObjectOutputStream output, ObjectInputStream input, BigInteger chave)
			throws IOException, ClassNotFoundException {
		Operacao operacao = new Operacao();
		operacao.setTipo(Tipo.DELETE);
		operacao.setChave(chave);
		output.writeObject(operacao);
		return (Operacao) input.readObject();
	}
}
