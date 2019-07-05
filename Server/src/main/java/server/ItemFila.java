package server;

import io.atomix.copycat.server.Commit;

import java.math.*;

public class ItemFila {
  Commit commit; // o objeto que "vai responder" para o cliente
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

  public void print() {
    if (value != null) {
      String z = new String(value);
      System.out.println(controll + " " + key + " " + z);
    } else {
      System.out.println(controll + " " + key);
    }
  }

  @Override
  public String toString() {
    if (value != null) {
      String z = new String(value);
      return (controll + " " + key + " " + z);
    } else {
      return (controll + " " + key);
    }

  }

}