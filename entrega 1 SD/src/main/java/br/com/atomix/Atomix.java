package br.com.atomix;

import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;
import java.util.List;

public class Atomix extends StateMachine {

    private AtomixReplica replica;
    private CopycatClient copycatClient;

    public AtomixReplica getReplica() {
        return replica;
    }

    public CopycatClient getCopycatClient() {
        return copycatClient;
    }

    public void start(int id, List<Address> addressList, int port) {
        List<Address> cluster = addressList;

        Storage storage = Storage.builder()
                .withDirectory(new File("log_"))
                .withStorageLevel(StorageLevel.DISK)
                .build();

        AtomixReplica replica1 = AtomixReplica.builder(
                new Address("localhost", port))
                .withStorage(storage)
                .withTransport(new NettyTransport())
                .build();

        if (id == 0) {
            replica = replica1.bootstrap().join();
        } else {
            replica = replica1.join(cluster).join();
        }


        CopycatClient client = CopycatClient.builder()
                .withTransport(NettyTransport.builder().withThreads(2).build())
                .build();


        copycatClient = client.connect(cluster).join();


        System.out.println("Replica inicializada");
    }


}
