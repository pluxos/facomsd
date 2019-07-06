package server.command;

import java.math.BigInteger;

import io.atomix.copycat.Query;

public class ReadQuery implements Query<byte[]> {
  private static final long serialVersionUID = 1L;
  public BigInteger key;

  public ReadQuery(BigInteger key) {
    this.key = key;
  }
}