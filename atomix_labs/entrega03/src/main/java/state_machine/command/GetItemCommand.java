package state_machine.command;

import io.atomix.copycat.Command;

import java.math.BigInteger;

public class GetItemCommand implements Command<Boolean> {
    String controll;
    BigInteger key;
    byte[] value;


    public GetItemCommand(String controll, BigInteger key, byte[] value) {
        this.controll = controll;
        this.key = key;
        this.value = value;
    }

    public String getControll() {
        return controll;
    }

    public BigInteger getKey() {
        return key;
    }


    public byte[] getValue() {
        return value;
    }
}
