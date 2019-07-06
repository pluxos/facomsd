package server.command;

import java.math.BigInteger;

import io.atomix.copycat.Command;
import io.atomix.copycat.Query;

public class CreateCommand implements Command<Boolean> {
  private static final long serialVersionUID = 1062088653144316612L;
  public BigInteger key;
  public byte[] value;

  public CreateCommand(BigInteger key, byte[] value) {
    this.key = key;
    this.value = value;
  }
}