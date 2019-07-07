package state_machine.command;

import io.atomix.copycat.Command;
import io.atomix.copycat.server.Commit;

import java.math.BigInteger;

public class CreateItemCommand implements Command<Boolean> {
    String controll;
    BigInteger key;
    String value;


    public CreateItemCommand(BigInteger key, String value) {
        this.controll = "CREATE";
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return (controll + " " + key + " " + value);
    }
    public String getControll() {
        return controll;
    }

    public BigInteger getKey() {
        return key;
    }


    public String getValue() {
        return value;
    }
}
