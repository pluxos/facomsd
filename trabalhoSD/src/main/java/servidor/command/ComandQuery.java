package servidor.command;

import java.math.BigInteger;

public class ComandQuery {

	/**
	 * 
	 * @param comando String comando
	 * @return inteiro 1 caso seja create; 2 caso read; 3 caso update; 4 caso delete
	 */
	public static int getTipoComando(String comando) {
		int tipo = -1;
		String stringtipo = comando.split(" ")[0].toLowerCase();
		stringtipo.toLowerCase();
		tipo = -1;
		if (stringtipo.equals("create")) {
			tipo = 1;
		} else {
			if (stringtipo.equals("read")) {
				tipo = 2;
			} else {
				if (stringtipo.equals("update")) {
					tipo = 3;
				} else {
					if (stringtipo.equals("delete")) {
						tipo = 4;
					}
				}
			}
		}

		if (stringtipo.equals("1") || stringtipo.equals("2") || stringtipo.equals("3") || stringtipo.equals("4")) {
			tipo = Integer.parseInt(stringtipo);
		}
		return tipo;
	}

	public static BigInteger getKey(String comando) throws Exception {

		if (comando.contains(":")) {
			return new BigInteger((comando.split(":")[0]).split(" ")[1]);
		} else {
			if (comando.split(" ").length > 1)
				return new BigInteger(comando.split(" ")[1]);
		}
		throw new Exception("Key vazia");
	}

	public static String getValue(String comando) throws Exception {
		// comando.indexOf(":")
		if (comando.contains(":")) {
			return comando.substring(comando.indexOf(":") + 1, comando.length());
		}
		throw new Exception("Formato invalido");

	}

}
