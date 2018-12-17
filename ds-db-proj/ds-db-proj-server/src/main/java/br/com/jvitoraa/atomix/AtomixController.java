package br.com.jvitoraa.atomix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.atomix.Atomix;
import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

public class AtomixController {
	
    private int port;
    private List<Address> cluster;
    private Atomix atomixReplica;

    public AtomixController(int port, List<Integer> addresses) {
        this.port = port;
        this.cluster = setupCluster(addresses);
    }

    public void connect() {

        Storage storage = Storage.builder()
                .withDirectory(new File("./integrity/atomix/" + port))
                .withStorageLevel(StorageLevel.DISK)
                .build();

        AtomixReplica replica = AtomixReplica.builder(
                new Address("localhost", port))
                .withStorage(storage)
                .withTransport(new NettyTransport())
                .build();

        if(cluster.isEmpty()) {
            atomixReplica = replica.bootstrap().join() ;
        }  else {
            atomixReplica = replica.join(cluster).join();
        }
        
        System.out.println("Replica initialized");
        
    }

    public Atomix getAtomixReplica() {
        return atomixReplica;
    }
    
    private List<Address> setupCluster(List<Integer> addresses) {
        List<Address> cluster = new ArrayList<>();
        for (int address: addresses) {
            cluster.add(new Address("localhost", address));
        }
        return cluster;
    }

}
