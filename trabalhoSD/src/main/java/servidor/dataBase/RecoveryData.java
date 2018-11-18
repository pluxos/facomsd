package servidor.dataBase;

import java.io.BufferedReader;
import java.io.FileReader;

import servidor.Finger;
import servidor.command.ExecuteCommand;

public class RecoveryData {
  public void recovery(Data data, Finger finger) {
    try {
      System.out.println("Recuperando banco de dados");
      ExecuteCommand execute = new ExecuteCommand();
      BufferedReader br = new BufferedReader(new FileReader("logs\\"+finger.getId().toString()+"\\operacoes.log"));
      String linha;
      while ((linha = br.readLine()) != null) {
        execute.execute(linha, data);
      }
      br.close();
    } catch (Exception erro) {
      System.out.println(erro.getMessage());
    }
  }
  
}
