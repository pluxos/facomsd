package state_machine.command;

import io.atomix.copycat.Command;

import java.math.BigInteger;

public class DeleteItemCommand implements Command<Boolean> {
    String controll;
    BigInteger key;


    public DeleteItemCommand( BigInteger key) {
        this.controll = "DELETE";
        this.key = key;
    }

    @Override
    public String toString() {
        return (controll + " " + key);
    }
    public String getControll() {
        return controll;
    }

    public BigInteger getKey() {
        return key;
    }

}
