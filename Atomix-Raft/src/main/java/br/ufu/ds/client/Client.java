package br.ufu.ds.client;

import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.map.DistributedMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) throws FileNotFoundException {
        Atomix atomix = Atomix.builder()
                .withMemberId("client")
                .withRack("rack-1")
                .withAddress("localhost:9000")
                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
                        .withNodes(
                                Node.builder()
                                        .withId("member-1")
                                        .withAddress("localhost:8001")
                                        .build(),
                                Node.builder()
                                        .withId("member-2")
                                        .withAddress("localhost:8002")
                                        .build(),
                                Node.builder()
                                        .withId("member-3")
                                        .withAddress("localhost:8003")
                                        .build())
                        .build()).build();

        atomix.start().join();

        DistributedMap<BigInteger, byte[]> database = atomix.<BigInteger, byte[]>mapBuilder("database").build();

        ExecutorService threadPool = Executors.newFixedThreadPool(1);

        // input for tests
        MenuImpl menu = null;
        if (args.length > 0) {
            File f = new File(args[0]);
            menu = new MenuImpl(atomix, new FileInputStream(f), database);
        } else {
            menu = new MenuImpl(atomix, database);
        }
        assert menu != null;
        threadPool.execute(menu);
    }

}
