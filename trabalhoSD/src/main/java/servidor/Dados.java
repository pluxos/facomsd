package servidor;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Dados implements Serializable {
  public Dados(long key, String texto) {
    this.key = key;
    this.texto = texto;
  }
  
  private String texto;
  private long key;
  
  public String getTexto() {
    return texto;
  }
  
  public void setTexto(String texto) {
    this.texto = texto;
  }
  
  public long getKey() {
    return key;
  }
  
  public void setKey(long key) {
    this.key = key;
  }
}
