package server.command;

import java.math.BigInteger;

import io.atomix.copycat.Command;
import io.atomix.copycat.Query;

public class UpdateCommand implements Command<Boolean> {
  private static final long serialVersionUID = -2145539382728333957L;
  public BigInteger key;
  public byte[] value;

  public UpdateCommand(BigInteger key, byte[] value) {
    this.key = key;
    this.value = value;
  }
}