package command;

import java.math.BigInteger;

import io.atomix.copycat.Command;

public class DeleteCommand implements Command<Boolean>
{
  private static final long serialVersionUID = 1L;
  public BigInteger key;

    public DeleteCommand(BigInteger key)
    {
      this.key = key;
    }
}