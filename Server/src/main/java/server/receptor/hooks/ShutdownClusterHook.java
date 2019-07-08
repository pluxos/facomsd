package server.receptor.hooks;

import io.atomix.core.Atomix;

public class ShutdownClusterHook implements Runnable {

    private Atomix cluster;

    public ShutdownClusterHook(Atomix context) {
        this.cluster = context;
    }

    @Override
    public void run() {

        System.err.println("*** shutting down Cluster");

        this.cluster.getValue("keyChord").close();
        this.cluster.getList("rangeChord").close();
        this.cluster.getMap("db").close();
        this.cluster.getMap("fingerTable").close();

        cluster.stop();
    }
}
