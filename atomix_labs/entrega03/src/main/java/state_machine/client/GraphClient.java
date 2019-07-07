package state_machine.client;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.LinkedList;
import java.util.List;

import state_machine.command.*;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;

public class GraphClient extends StateMachine {
    public static void main(String[] args) {
        List<Address> addresses = new LinkedList<>();

        CopycatClient.Builder builder = CopycatClient.builder()
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build());
        CopycatClient client = builder.build();

        for (int i = 0; i < args.length; i += 2) {
            Address address = new Address(args[i], Integer.parseInt(args[i + 1]));
            addresses.add(address);
        }

        CompletableFuture<CopycatClient> future = client.connect(addresses);
        future.join();

        CompletableFuture[] futures = new CompletableFuture[]{
            client.submit(new CreateItemCommand(new BigInteger("123"),"pororo"))
        };

        CompletableFuture.allOf(futures).thenRun(() -> System.out.println("Commands completed!"));


        try {
            System.out.println("1: " + client.submit(new ReadItemQuery( new BigInteger("123") )).get());
            client.submit(new UpdateItemCommand(new BigInteger("123"),"adfs")).get();
            client.submit(new DeleteItemCommand(new BigInteger("123")));
//            System.out.println("2: " + client.submit(new GetVertexQuery(2)).get());
        } catch (Exception e) {
            System.out.println("Commands may have failed.");
            e.printStackTrace();
        }

        client.submit(new ReadItemQuery(new BigInteger("1234"))).thenAccept(result -> {
            System.out.println("1-2: " + result);
        });
    }
}
