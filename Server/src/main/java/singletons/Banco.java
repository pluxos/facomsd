package singletons;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


public class Banco {

  public Map<BigInteger,byte[]> database= new HashMap<BigInteger, byte[]>();

  private static Banco ourInstance = new Banco();

  public static Banco getInstance() {
	return ourInstance;
  }

  private Banco() {
  }

  public Boolean Insert (BigInteger key, byte[] value){
	if (this.database.containsKey(key)){
	  return false;   // Se a chave já existir no banco, ele retorna falso
	} else {
	  this.database.put(key, value);  // Se não existir, ele insere a chave e retorna verdadeiro
	  return true;
	}
  }

  public byte[] Read (BigInteger key) {
	if( this.database.containsKey(key) ) {
	  return this.database.get(key);
	}
	else return null;
  }

  public Boolean Update (BigInteger key, byte[] value) {
	if (this.database.containsKey(key)) {
	  this.database.put(key, value); //  Se a chave já existir, o valor dela é sobrescrito
	  return true;
	} else {
	  return false; //  Não existe a chave especificada
	}
  }

  public Boolean Delete (BigInteger key) {
	if (this.database.containsKey(key)) {   // Se a chave existir ele remove e retorna verdadeiro, caso contrário, retorna falso
	  this.database.remove(key);
	  return true;
	} else {
	  return false;
	}
  }
}
  