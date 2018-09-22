package br.ufu.repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class CrudRepository {

    private Map<BigInteger, String> database = new HashMap<>();

    public String create(BigInteger id, String value) throws DatabaseException {
        if (database.containsKey(id)) {
            throw new DatabaseException("ID já cadastrado na base");
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
            throw new DatabaseException("ID inexistente na base");
        }
    }

}
