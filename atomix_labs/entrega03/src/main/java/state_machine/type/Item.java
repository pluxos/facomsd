package state_machine.type;

import io.atomix.copycat.server.Commit;
import java.io.Serializable;
import java.math.*;

public class Item implements Serializable {
  String controll;
  BigInteger key;
  byte[] value;

  public Item(String controll, BigInteger key, byte[] value) {
    this.controll = controll;
    this.key = key;
    this.value = value;
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