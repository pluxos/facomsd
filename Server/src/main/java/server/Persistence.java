package server;

import java.util.concurrent.BlockingQueue;

import io.atomix.copycat.server.Commit;
import server.query.CreateQuery;
import server.query.DeleteQuery;
import server.query.UpdateQuery;

import java.math.BigInteger;
import java.lang.*;
import java.io.*;
import java.net.*;

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

    private synchronized boolean create(Commit<CreateQuery> commit, BigInteger key, byte[] value) {
        try {
            return database.Insert(key, value);
        } finally {
            commit.close();
        }
    }

    private synchronized boolean update(Commit<UpdateQuery> commit, BigInteger key, byte[] value) {
        try {
            return database.Update(key, value);
        } finally {
            commit.close();
        }
    }

    private synchronized boolean delete(Commit<DeleteQuery> commit, BigInteger key) {
        try {
            return database.Delete(key);
        } finally {
            commit.close();
        }
    }

    private synchronized byte[] read(Commit<DeleteQuery> commit, BigInteger key) {
        try {
            return database.Read(key);
        } finally {
            commit.close();
        }
    }

}