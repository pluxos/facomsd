package query;

import java.math.BigInteger;

import io.atomix.copycat.Query;

public class CreateQuery implements Query<Boolean>
{
    private static final long serialVersionUID = 1062088653144316612L;
    public BigInteger key;
    public byte [] value;

    public CreateQuery(BigInteger key, byte[] value)
    {
      this.key = key;
      this.value = value;
    }
}