package server;

import java.util.concurrent.BlockingQueue;

import io.atomix.copycat.server.Commit;
import server.command.CreateCommand;
import server.command.DeleteCommand;
import server.command.UpdateCommand;

import java.math.BigInteger;
import java.lang.*;

public class Persistence implements Runnable {

    protected BlockingQueue<ItemFila> f3;
    private Kv database = new Kv();

    public Persistence() {
        this.f3 = F3.getInstance();
    }

    @Override
    public void run() {
        ItemFila obj;
        Commit commit;

        try {
            while (true) {
                obj = f3.take();
                commit = obj.commit;
                switch (obj.controll) {
                case CREATE: {
                    create(commit, obj.key, obj.value);
                    break;
                }
                case UPDATE: {
                    update(commit, obj.key, obj.value);
                    break;
                }
                case READ: {
                    read(commit, obj.key);
                    break;
                }
                case DELETE: {
                    delete(commit, obj.key);
                    break;
                }
                default: {
                    throw new UnsupportedOperationException();
                }

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private synchronized boolean create(Commit<CreateCommand> commit, BigInteger key, byte[] value) {
        try {
            return database.Insert(key, value);
        } finally {
            commit.close();
        }
    }

    private synchronized boolean update(Commit<UpdateCommand> commit, BigInteger key, byte[] value) {
        try {
            return database.Update(key, value);
        } finally {
            commit.close();
        }
    }

    private synchronized boolean delete(Commit<DeleteCommand> commit, BigInteger key) {
        try {
            return database.Delete(key);
        } finally {
            commit.close();
        }
    }

    private synchronized byte[] read(Commit<DeleteCommand> commit, BigInteger key) {
        try {
            return database.Read(key);
        } finally {
            commit.close();
        }
    }

}