package br.ufu.repository;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CrudRepository {

    private final Map<BigInteger, String> database = new ConcurrentHashMap<>();

    public String create(BigInteger id, String value) throws DatabaseException {
        if (database.containsKey(id)) {
            throw new DatabaseException("ID " + id + " já cadastrado na base");
        }
        database.put(id, value);
        return value;
    }

    public String update(BigInteger id, String value) throws DatabaseException {
        checkExistence(id);
        database.put(id, value);
        return value;
    }

    public String read(BigInteger id) throws DatabaseException {
        checkExistence(id);
        return database.get(id);
    }

    public String delete(BigInteger id) throws DatabaseException {
        checkExistence(id);
        return database.remove(id);
    }

    private void checkExistence(BigInteger id) throws DatabaseException {
        if (!database.containsKey(id)) {
            throw new DatabaseException("ID " + id + " inexistente na base");
        }
    }

    public Map<BigInteger, String> getDatabase() { return database; }

}