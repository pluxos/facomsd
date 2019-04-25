package client.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
