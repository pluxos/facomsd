package server.commons.domain;

import java.io.PrintStream;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(value = {"output"})
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GenericCommand {

    private PrintStream output;
    private String method;
    private BigInteger code;
    private byte[] data;

    public GenericCommand() {}

    public GenericCommand(PrintStream output, String method, BigInteger code) {
        this.code = code;
        this.data = null;
        this.method = method;
        this.output = output;
    }

    public GenericCommand(PrintStream output, String method, BigInteger code, byte[] data) {
        this.code = code;
        this.data = data;
        this.method = method;
        this.output = output;
    }

    public PrintStream getOutput() {
        return output;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public BigInteger getCode() {
        return code;
    }

    public void setCode(BigInteger code) {
        this.code = code;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
