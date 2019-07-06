package state_machine.command;

import io.atomix.copycat.Command;
import io.atomix.copycat.server.Commit;

import java.math.BigInteger;

public class AddItemFilaCommand implements Command<Boolean>
{
    Commit commit;
    Controll controll;
    BigInteger key;
    byte[] value;


    public AddItemFilaCommand(Commit commit, Controll controll, BigInteger key, byte[] value)
    {
        this.commit = commit;
        this.controll = controll;
        this.key = key;
        this.value = value;
    }
}
