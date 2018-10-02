package servidor;

import java.io.ObjectOutputStream;

import servidor.dataBase.Data;

public class ClientData {
  private String comando;
  private ObjectOutputStream out;
  private Data data;
  
  public Data getData() {
    return data;
  }
  
  public void setData(Data data) {
    this.data = data;
  }
  
  public ObjectOutputStream getOut() {
    return out;
  }
  
  public void setOut(ObjectOutputStream out) {
    this.out = out;
  }
  
  public String getComando() {
    return comando;
  }
  
  public void setComando(String comando) {
    this.comando = comando;
  }
}
