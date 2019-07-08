package atomix_lab.state_machine.server;

import java.math.BigInteger;

public class Comando {

  private int operacao;
	private BigInteger chave;
	private byte[] valor;

  public Comando(int op, BigInteger ch) {
		this.operacao = op;
		this.chave = ch;
	}

	public Comando(int op, BigInteger ch, byte[] val) {
		this.operacao = op;
		this.chave = ch;
		this.valor = val;
	}

	public int getOperacao() {
		return operacao;
	}
	public BigInteger getChave() {
		return chave;
	}
	public byte[] getValor() {
		return valor;
	}

  public byte[] getData() {
		return valor;
	}

}
