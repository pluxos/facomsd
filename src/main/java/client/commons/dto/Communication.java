package client.commons.dto;

import java.math.BigInteger;

public class Communication {
    public String command;
    public BigInteger code;
    public byte[] data;

    public Communication(String command, BigInteger code, byte[] data){
        this.code = code;
        this.command = command;
        this.data = data;
    }

    public Communication(String command, BigInteger code){
        this.code = code;
        this.command = command;
        this.data = null;
    }
}
