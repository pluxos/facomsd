package server.model.HashMap;

import java.math.BigInteger;
import java.util.HashMap;

public class Manipulator {
    private volatile static HashMap<BigInteger, byte[]> db = new HashMap<>();

    public static void addValue(BigInteger code, byte[] data) {
        db.put(code, data);
    }

    public static void removeValue(BigInteger code) {
        db.remove(code);
    }

    public static byte[] getValue(BigInteger code) {
        return db.get(code);
    }

    public static void updateValue(BigInteger code, byte[] data) {
        db.replace(code, data);
    }
}
