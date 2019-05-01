package br.ufu.ds.server;

import com.google.protobuf.ByteString;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcus
 * @author Lucas Tannus
 */
public class Database {

    private Map<BigInteger, ByteString> mHashTable;

    private Database() {
        mHashTable = new HashMap<>();
    }

    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null)
                    instance = new Database();
            }
        }

        return instance;
    }

    /**
     * Insere um novo registro no mHashTable
     *
     * @param data ByteString   Novo dado a ser inserido
     */
    public synchronized void create(BigInteger key, ByteString data)
            throws DatabaseException {
        if (mHashTable.containsKey(key))
            throw new DatabaseException(String.format("Key %d already exists", key.longValue()));
        mHashTable.put(key, data);
    }

    /**
     * Retorna o registro do banco de dados referente a uma chave passada
     * Caso não encontrada a chave retorn null
     *
     * @param key BigInteger        Chave para a busca do registro
     * @return ByteString | null    Dado de retorno
     */
    public ByteString read(BigInteger key) {
        return mHashTable.get(key);
    }

    /**
     * Altera um dado já existente no banco com um novo valor
     *
     * @param key BigInteger        Chave do registro a ser modificado
     * @param newData ByteString    Novo valor do registro
     */
    public synchronized void update(BigInteger key, ByteString newData)
                throws DatabaseException {
        if (!mHashTable.containsKey(key))
            throw new DatabaseException(String.format("Key %d already exists", key.longValue()));
        mHashTable.put(key, newData);
    }

    /**
     * Deleta um registro por meio de sua chave
     *
     * @param key BigInteger    Chave do registro que será deletado
     * @return boolean
     */
    public synchronized ByteString delete(BigInteger key) {
        return mHashTable.remove(key);
    }

}
