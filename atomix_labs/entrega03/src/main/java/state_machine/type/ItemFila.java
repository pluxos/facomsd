package state_machine.type;

import io.atomix.copycat.server.Commit;

import java.io.Serializable;
import java.math.*;

public class ItemFila implements Serializable {
  Commit commit;
  Controll controll;
  BigInteger key;
  byte[] value;

  public ItemFila(Commit commit, Controll controll, BigInteger key, byte[] value) {
    this.commit = commit;
    this.controll = controll;
    this.key = key;
    this.value = value;
  }

  public ItemFila(Commit commit, Controll controll, BigInteger key) {
    this.commit = commit;
    this.controll = controll;
    this.key = key;
  }

  @Override
  public String toString() {
    if (value != null) {
      String z = new String(value);
      return (controll.getDescricao() + " " + key + " " + z);
    } else {
      return (controll.getDescricao() + " " + key);
    }

  }

}