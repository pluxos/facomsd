package util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Banco {

	Map<BigInteger, String> banco;
	private Banco elemento;
	
	public Banco(){
		
		banco = new HashMap<BigInteger, String>();
	}
	
	public void setValor(BigInteger chave, String valor) {
		banco.put(chave,valor);
	}
	
	public String getValor(BigInteger chave) {
		return banco.get(chave);
	}
	
	public List<String> getElementos() {
		
		List<String> lista = new ArrayList<String>();
		elemento = null;
		
		for (BigInteger chave : banco.keySet()) {
            
			//Capturamos o valor a partir da chave
            String valor = elemento.getValor(chave);
            lista.add("Chave: " + chave + ", valor: " + valor);
		}
		
		return lista;
	}
	
	public void deletaValor(BigInteger chave) {
		if(banco.containsKey(chave)) {
			banco.remove(chave);
		}
	}
	
	public void atualizaValor(BigInteger chave, String valor) {
		if(banco.containsKey(chave)) {
			banco.replace(chave, valor);
		}
	}
	
	public boolean existeElemento(BigInteger chave){
		
		if(banco.containsKey(chave)){
			return true;
		} else {
			return false;
		}
		
	}
	
}
