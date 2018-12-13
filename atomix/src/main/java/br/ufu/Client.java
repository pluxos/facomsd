package br.ufu;

import io.atomix.AtomixClient;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;

import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        List<Address> cluster = Arrays.asList(
                new Address("127.0.0.1", 4445),
                new Address("127.0.0.1", 4446),
                new Address("127.0.0.1", 4447)
        );
        AtomixClient client = AtomixClient.builder()
                .withTransport(new NettyTransport())
                .build();
        client.connect(cluster)
                .thenRun(() -> {
                    System.out.println("Client is connected to the cluster!");
                });

    }
}
