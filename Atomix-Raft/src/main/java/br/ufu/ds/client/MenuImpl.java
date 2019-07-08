package br.ufu.ds.client;

import io.atomix.core.Atomix;
import io.atomix.core.map.DistributedMap;

import java.io.InputStream;
import java.math.BigInteger;

public class MenuImpl extends MenuListener {

    private DistributedMap<BigInteger, byte[]> database;
    private Atomix connection;

    public MenuImpl(Atomix connection, DistributedMap<BigInteger, byte[]> database) {
        this.database = database;
        this.connection = connection;
    }

    public MenuImpl(Atomix conn, InputStream in, DistributedMap<BigInteger, byte[]> database) {
        super(in);
        this.connection = conn;
        this.database = database;
    }

    @Override
    protected void onCreateSelected(BigInteger key, byte[] value) {
        if (database.containsKey(key)) {
            System.out.println(String.format("Create FAIL -> %d already exists", key));
        } else {
            database.put(key, value);
            System.out.println(String.format("Create OK -> [%d]: %s", key, new String(value)));
        }
    }

    @Override
    protected void onReadSelected(BigInteger key) {
        byte[] value = database.get(key);
        if (value != null && value.length > 0) {
            System.out.println(String.format("Read OK -> [%d] = %s", key.intValue(), new String(value)));
        } else {
            System.out.println(String.format("Read FAIL -> Can't find %d", key));
        }
    }

    @Override
    protected void onUpdateSelected(BigInteger key, byte[] value) {
        if (database.containsKey(key)) {
            database.put(key,value);
            System.out.println(String.format("Update OK -> [%d]: %s",  key, new String(value)));
        } else {
            System.out.println(String.format("Update FAIL -> %d not exists", key));
        }
    }

    @Override
    protected void onDeleteSelected(BigInteger key) {
        if (database.containsKey(key)) {
            byte[] data = database.remove(key);
            System.out.println("Delete OK -> " + new String(data));
        } else {
            System.out.println(String.format("Delete FAIL -> %d", key));
        }
    }

    @Override
    protected void onExit() {
        System.out.println("server is down");
        connection.stop();
        System.out.println("bye");
        System.exit(0);
    }
}
