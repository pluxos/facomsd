package br.ufu;

import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class AtomixCluster {

    public static void main(String[] args){
        List<Address> cluster = Arrays.asList(
                new Address("localhost", 4445),
                new Address("localhost", 4446),
                new Address("localhost", 4447)
        );

        Storage storage = Storage.builder()
                .withDirectory(new File("logs"))
                .withStorageLevel(StorageLevel.DISK)
                .build();


        AtomixReplica replica1 = AtomixReplica
                .builder(cluster.get(0))
                .withStorage(storage)
                .withTransport(new NettyTransport())
                .build();
        replica1.bootstrap(cluster).join();

        AtomixReplica replica2 = AtomixReplica
                .builder(cluster.get(1))
                .withStorage(storage)
                .withTransport(new NettyTransport())
                .build();
        replica2.bootstrap(cluster).join();

        AtomixReplica replica3 = AtomixReplica
                .builder(cluster.get(2))
                .withStorage(storage)
                .withTransport(new NettyTransport())
                .build();
        replica3.bootstrap(cluster).join();
    }
}
