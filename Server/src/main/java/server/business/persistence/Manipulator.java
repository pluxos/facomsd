package server.business.persistence;

import io.atomix.core.map.DistributedMap;
import server.commons.atomix.ClusterAtomix;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manipulator {
	
	private Manipulator() {}
	
    private static volatile DistributedMap<BigInteger, byte[]> db;

	public static void setDb(DistributedMap<BigInteger, byte[]> distributedMap) {
	    db = distributedMap;
    }

    public static void addValue(BigInteger code, byte[] data) {
        db.put(code, data);
        ClusterAtomix.getDb().put(code, data);
    }

    public static void addValues(Map<BigInteger, byte[]> values) {
        db.putAll(values);
        ClusterAtomix.getDb().putAll(values);
    }

    public static void removeValue(BigInteger code) {
        db.remove(code);
        ClusterAtomix.getDb().remove(code);
    }

    public static byte[] getValue(BigInteger code) {
        return db.get(code);
    }

    public static void updateValue(BigInteger code, byte[] data) {
        db.replace(code, data);
        ClusterAtomix.getDb().replace(code, data);
    }

    public static boolean containKey(BigInteger code) {
        return db.containsKey(code);
    }
    
    public static DistributedMap<BigInteger, byte[]> getDb() {
    	return db;
    }
    
    public static void clearDatabase() {
    	db.clear();
    	ClusterAtomix.getDb().clear();
    }

    public static HashMap<BigInteger, byte[]> removeValues(List<Integer> range) {
        HashMap<BigInteger, byte[]> res = new HashMap<>();

        range.forEach((key) -> {

            if(db.containsKey(BigInteger.valueOf(key))){
                res.put(BigInteger.valueOf(key), db.get(BigInteger.valueOf(key)));
                db.remove(BigInteger.valueOf(key));
                ClusterAtomix.getDb().remove(BigInteger.valueOf(key));
            }
        });

        return res;
    }
}