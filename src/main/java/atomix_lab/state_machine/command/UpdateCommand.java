package atomix_lab.state_machine.command;

import java.math.BigInteger;

import io.atomix.copycat.Command;

public class UpdateCommand implements Command<Boolean>
{
    public BigInteger key;
    public byte [] value;

    public UpdateCommand(BigInteger chave, String valor) {
        this.key = chave;
        this.value = valor.getBytes();
    }
}
