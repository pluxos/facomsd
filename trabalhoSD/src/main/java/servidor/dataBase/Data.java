package servidor.dataBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import servidor.command.ExecuteCommand;

public class Data {
  static Map<BigInteger, String> dados = new HashMap<BigInteger, String>();
  // static Semaphore mutex = new Semaphore(1);
  
  public synchronized static void recovery() {
	  try{
			System.out.println("Recuperando banco de dados");
			ExecuteCommand execute = new ExecuteCommand();
			BufferedReader br = new BufferedReader(new	FileReader("operacoes.log"));
			String linha;
			while ((linha = br.readLine()) != null) {
			     execute.execute(linha);
			}
			br.close();
		}catch (Exception erro){
			System.out.println(erro.getMessage());
		}
    // dados.put(1.0, "Teste1");
    // dados.put(2, "Teste2");
    // dados.put(3, "Teste3");
    // dados.put(4, "Teste4");
  }
  
  private synchronized static boolean validaExistencia(BigInteger key) {
    if (!dados.containsKey(key)) {
      return false;
    }
    return true;
  }
  
  public synchronized static String create(BigInteger key, String value) throws Exception {
    if (dados.containsKey(key)) {
      return "Dados ja cadastrados";
    }
    dados.put(key, value);
    return "Dados criados com sucesso";
  }
  
  public synchronized static String read(BigInteger key) throws Exception {
    if (validaExistencia(key))
      return dados.get(key);
    else
      return "Dados nao encontrados";
  }
  
  public synchronized static String update(BigInteger key, String value) throws Exception {
    if (validaExistencia(key)) {
      dados.put(key, value);
      return "Dados alterados com sucesso";
    }
    else
      return "Dados nao encontrados";
  }
  
  public synchronized static String delete(BigInteger key) throws Exception {
    if (validaExistencia(key)) {
      dados.remove(key);
      return "Dados removidos com sucesso";
    }
    else
      return "Key nao encontrada";
  }
}
