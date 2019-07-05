package query;

import java.math.BigInteger;

import io.atomix.copycat.Query;

public class DeleteQuery implements Query<Boolean>
{
  private static final long serialVersionUID = 1L;
  public BigInteger key;

    public DeleteQuery(BigInteger key)
    {
      this.key = key;
    }
}