package atomix_lab.state_machine.server;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Record {

	private BigInteger key;
	private String label;
	private byte[] data;

	// Estrutura para agrupar todos os dados
	Record(BigInteger key, String label) {
		this.key = key;
		this.label = label;
		this.data = null;
	}

	Record(BigInteger key, String label, byte[] data) {
		this.key = key;
		this.label = label;
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}
	public BigInteger getKey() {
		return key;
	}
	public String getLabel() {
		return label;
	}
}
