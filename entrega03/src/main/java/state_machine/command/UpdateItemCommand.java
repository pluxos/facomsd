package state_machine.command;

import io.atomix.copycat.Command;

import java.math.BigInteger;

public class UpdateItemCommand implements Command<Boolean> {
    String controll;
    BigInteger key;
    String value;


    public UpdateItemCommand( BigInteger key, String value ) {
        this.controll = "UPDATE";
        this.key = key;
        this.value = value;
    }

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
