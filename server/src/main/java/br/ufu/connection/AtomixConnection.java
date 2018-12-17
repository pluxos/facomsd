package br.ufu.connection;

import io.atomix.Atomix;
import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AtomixConnection {

    private int port;
    private List<Address> cluster;
    private Atomix atomixReplica;

    public AtomixConnection(int port, List<Integer> addresses) {
        this.port = port;
        this.cluster = getCluster(addresses);
    }

    public void connect() {

        Storage storage = Storage.builder()
                .withDirectory(new File("/tmp/atomix-logs/" + port))
                .withStorageLevel(StorageLevel.DISK)
                .build();

        AtomixReplica replica = AtomixReplica.builder(
                new Address("localhost", port))
                .withStorage(storage)
                .withTransport(new NettyTransport())
                .build();

        if(cluster.isEmpty()) {
            atomixReplica = replica.bootstrap().join();
        } else if(cluster.size() == 1) {
            atomixReplica = replica.join(cluster.get(0)).join();
        } else {
            atomixReplica = replica.join(cluster).join();
        }
    }

    private List<Address> getCluster(List<Integer> addresses) {
        List<Address> cluster = new ArrayList<>();
        for (int address: addresses) {
            cluster.add(new Address("localhost", address));
        }
        return cluster;
    }

    public Atomix getAtomixReplica() {
        return atomixReplica;
    }

}
