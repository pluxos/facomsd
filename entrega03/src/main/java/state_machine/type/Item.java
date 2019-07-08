package state_machine.type;

import java.io.Serializable;
import java.math.*;

public class Item implements Serializable {
  private String controll;
  private BigInteger key;
  private String value;

  public Item(String controll, BigInteger key, String value) {
    this.controll = controll;
    this.key = key;
    this.value = value;
  }

  public String getControll() {
    return controll;
  }

  public BigInteger getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    if (value != null) {
      return (controll + " " + key + " " + value);
    } else {
      return (controll + " " + key);
    }
  }

}