package servidor;

import java.util.Map;

public class Data {
	public static void recovery(Map<Long, Dados> dados) {

		

		dados.put(1L, new Dados(1, "Teste1"));
		dados.put(2L, new Dados(2, "Teste2"));
		dados.put(3L, new Dados(3, "Teste3"));
		dados.put(4L, new Dados(4, "Teste4"));
		
	}

}
