package server.commons.domain;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.grpc.stub.StreamObserver;

@JsonIgnoreProperties(value = {"output"})
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GenericCommand {

    private StreamObserver output;
    private String method;
    private BigInteger code;
    private byte[] data;

    public GenericCommand() {}

    public GenericCommand(StreamObserver output, String method, BigInteger code) {
        this.code = code;
        this.data = null;
        this.method = method;
        this.output = output;
    }

    public GenericCommand(StreamObserver output, String method, BigInteger code, byte[] data) {
        this.code = code;
        this.data = data;
        this.method = method;
        this.output = output;
    }

    public StreamObserver getOutput() {
        return output;
    }

    public void setOutput(StreamObserver output) {
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
