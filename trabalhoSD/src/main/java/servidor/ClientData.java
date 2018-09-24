package servidor;

import java.io.ObjectOutputStream;

public class ClientData {
  private String comando;
  private ObjectOutputStream out;
  
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
