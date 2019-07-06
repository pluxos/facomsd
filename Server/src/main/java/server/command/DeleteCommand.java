package server.command;

import java.math.BigInteger;

import io.atomix.copycat.Command;
import io.atomix.copycat.Query;

public class DeleteCommand implements Command<Boolean> {
  private static final long serialVersionUID = 1L;
  public BigInteger key;

  public DeleteCommand(BigInteger key) {
    this.key = key;
  }
}