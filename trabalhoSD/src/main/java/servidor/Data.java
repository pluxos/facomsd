package servidor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Data {

	static Map<BigInteger, String> dados = new HashMap<BigInteger, String>();
	// static Semaphore mutex = new Semaphore(1);

	public synchronized static void recovery() {

//		dados.put(1.0,  "Teste1");
//		dados.put(2,  "Teste2");
//		dados.put(3, "Teste3");
//		dados.put(4, "Teste4");

	}

	private synchronized static boolean validaExistencia(BigInteger key) {
		if (!dados.containsKey(key)) {
			return false;
		}
		return true;
	}

	public synchronized static String create(BigInteger key, String value) {
		if (dados.containsKey(key)) {
			return "Key ja cadastrada";
		}
		dados.put(key, value);
		return "Dados Criados com sucesso";
	}

	public synchronized static String read(BigInteger key) {
		if (validaExistencia(key))
			return dados.get(key);
		else
			return "Dados nao encontrados";
	}

	public synchronized static String update(BigInteger key, String value) {
		if (validaExistencia(key)) {
			dados.put(key, value);
			return "Dados alterados com sucesso";
		} else
			return "Key nao encontrada";
	}

	public synchronized static String delete(BigInteger key) {
		if (validaExistencia(key)) {
			dados.remove(key);
			return "key removida com sucesso";
		} else
			return "Key nao encontrada";
	}

}
