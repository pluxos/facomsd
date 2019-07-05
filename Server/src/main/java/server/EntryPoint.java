package server;

import io.atomix.copycat.server.Commit;
import server.query.CreateQuery;
import server.query.DeleteQuery;
import server.query.ReadQuery;
import server.query.UpdateQuery;

class EntryPoint {

    ItemFila createItemFila() {
        return null;
    }

    public static void create(Commit<CreateQuery> commit) {
        try {
            CreateQuery createQuery = commit.command();
            ItemFila newItem = new ItemFila(commit, Controll.CREATE, createQuery.key, createQuery.value);

            F1.getInstance().put(newItem);
        } catch (InterruptedException e) {
            commit.close();
            e.printStackTrace();
        }
    }

    public static void read(Commit<ReadQuery> commit) {
        try {
            ReadQuery readQuery = commit.command();
            ItemFila newItem = new ItemFila(commit, Controll.READ, readQuery.key);

            F1.getInstance().put(newItem);
        } catch (InterruptedException e) {
            commit.close();
            e.printStackTrace();
        }
    }

    public static void delete(Commit<DeleteQuery> commit) {
        try {
            DeleteQuery deleteQuery = commit.command();
            ItemFila newItem = new ItemFila(commit, Controll.DELETE, deleteQuery.key);

            F1.getInstance().put(newItem);
        } catch (InterruptedException e) {
            commit.close();
            e.printStackTrace();
        }
    }

    public static void update(Commit<UpdateQuery> commit) {
        try {
            UpdateQuery updateQuery = commit.command();
            ItemFila newItem = new ItemFila(commit, Controll.UPDATE, updateQuery.key, updateQuery.value);

            F1.getInstance().put(newItem);
        } catch (InterruptedException e) {
            commit.close();
            e.printStackTrace();
        }
    }
}