package br.ufu.ds.server;

import br.ufu.ds.rpc.Request;
import com.google.protobuf.ByteString;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author Marcus
 * @author Lucas Tannus
 */
public final class Database {

    private HashMap<BigInteger, ByteString> mHashTable;

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

    public synchronized void setDatabase(HashMap<BigInteger, ByteString> map) {
        this.mHashTable = null;
        this.mHashTable = map;
    }

    public HashMap<BigInteger, ByteString> getDb() {
        return mHashTable;
    }

    public HashMap<BigInteger, ByteString> getDbCopy() {
        return new HashMap<>(mHashTable);
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

    public void create(Request request)
            throws DatabaseException {
        create(BigInteger.valueOf(request.getId()), request.getData());
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

    public ByteString read(Request key) {
        return mHashTable.get(BigInteger.valueOf(key.getId()));
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
            throw new DatabaseException(String.format("Key %d not found", key.longValue()));
        mHashTable.put(key, newData);
    }

    public void update(Request request)
            throws DatabaseException {
        update(BigInteger.valueOf(request.getId()), request.getData());
    }

    /**
     * Deleta um registro por meio de sua chave
     * @param key BigInteger    Chave do registro que será deletado
     * @return boolean
     */
    public synchronized ByteString delete(BigInteger key) {
        return mHashTable.remove(key);
    }

    public ByteString delete(Request key) {
        return delete(BigInteger.valueOf(key.getId()));
    }
}
