package query;

import java.math.BigInteger;

import io.atomix.copycat.Query;

public class UpdateQuery implements Query<Boolean>
{
  private static final long serialVersionUID = -2145539382728333957L;
  public BigInteger key;
    public byte [] value;

    public UpdateQuery(BigInteger key, byte[] value)
    {
      this.key = key;
      this.value = value;
    }
}