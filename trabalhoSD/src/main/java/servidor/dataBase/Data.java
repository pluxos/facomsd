package servidor.dataBase;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Data {
  private Map<BigInteger, String> dados = new HashMap<BigInteger, String>();
  
  public Map<BigInteger, String> getDados() {
    return dados;
  }
  
  private boolean validaExistencia(BigInteger key) {
    if (!dados.containsKey(key)) {
      return false;
    }
    return true;
  }
  
  public String create(BigInteger key, String value) throws Exception {
    if (dados.containsKey(key)) {
      return "Dados ja cadastrados";
    }
    dados.put(key, value);
    return "Dados criados com sucesso";
  }
  
  public String read(BigInteger key) throws Exception {
    if (validaExistencia(key))
      return dados.get(key);
    else
      return "Dados nao encontrados";
  }
  
  public String update(BigInteger key, String value) throws Exception {
    if (validaExistencia(key)) {
      dados.put(key, value);
      return "Dados alterados com sucesso";
    }
    else
      return "Dados nao encontrados";
  }
  
  public String delete(BigInteger key) throws Exception {
    if (validaExistencia(key)) {
      dados.remove(key);
      return "Dados removidos com sucesso";
    }
    else
      return "Key nao encontrada";
  }
}
