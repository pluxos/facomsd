package state_machine.command;

import io.atomix.copycat.Command;
import io.atomix.copycat.Query;

import java.math.BigInteger;

public class ReadItemQuery implements Query<byte[]> {
    BigInteger key;
    String controll;

    public ReadItemQuery(BigInteger key) {
        this.controll = "READ";
        this.key = key;
    }
    @Override
    public String toString() {
        return (controll + " " + key);
    }
    public BigInteger getKey() {
        return key;
    }

}
