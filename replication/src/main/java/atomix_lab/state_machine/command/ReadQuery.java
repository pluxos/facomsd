package atomix_lab.state_machine.command;

import java.math.BigInteger;

import atomix_lab.state_machine.type.Data;
import io.atomix.copycat.Query;

public class ReadQuery implements Query<Data>
{
    public BigInteger key;

    public ReadQuery(BigInteger key)
    {
        this.key = key;
    }
}
