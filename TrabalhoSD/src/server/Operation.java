package server;

import java.math.BigInteger;

public class Operation {

    private BigInteger key;
    private String value;
    private Integer operation;

    public Operation(BigInteger key, String value, Integer operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    BigInteger getKey() {
        return key;
    }

    public void setKey( BigInteger key ) {
        this.key = key;
    }

    String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    Integer getOperation() {
        return operation;
    }

    public void setOperation( Integer operation ) {
        this.operation = operation;
    }

    @Override
    public String toString(){
        return (
                "Operation: " + operation + "\n" +
                "Key:       " + key       + "\n" +
                "Message:   " + value     + "\n"
        );
    }
}
