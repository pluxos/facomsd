package server.commons.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.grpc.GenericResponse;
import io.grpc.stub.StreamObserver;

import java.math.BigInteger;

@JsonIgnoreProperties(value = {"output"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericCommand {

    private StreamObserver<GenericResponse> output;
    private String method;
    private BigInteger code;
    private byte[] data;

    public GenericCommand() {}

    public GenericCommand(StreamObserver<GenericResponse> output, String method) {
        this.method = method;
        this.output = output;
    }

    public StreamObserver<GenericResponse> getOutput() {
        return output;
    }

    public void setOutput(StreamObserver<io.grpc.GenericResponse> output) {
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
