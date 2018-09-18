package br.ufu.repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class CrudRepository {

    private BigInteger sequence = BigInteger.ZERO;

    private Map<BigInteger, String> database = new HashMap<>();

    private BigInteger nextVal() {
        sequence = sequence.add(BigInteger.ONE);
        return sequence;
    }

    public String create(String value) {
        BigInteger id = nextVal();
        database.put(id, value);
        return value;
    }

    public String update(BigInteger id, String value) {
        database.put(id, value);
        return value;
    }

    public String read(BigInteger id) {
        return database.get(id);
    }

    public String delete(BigInteger id) {
        return database.remove(id);
    }

}
