package state_machine.server;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import state_machine.command.*;
import state_machine.type.Item;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

public class GraphStateMachine extends StateMachine {
    @SuppressWarnings("unused")
    public Boolean AddItemFila(Commit<CreateItemCommand> commit) {
        try {
            CreateItemCommand aec = commit.operation();
            Item i = new Item(aec.getControll(), aec.getKey(), aec.getValue());
            System.out.println(aec.toString());
            return Boolean.TRUE;
        } finally {
            commit.close();
        }
    }
    @SuppressWarnings("unused")
    public Boolean UpdateItemFila(Commit<UpdateItemCommand> commit) {
        try {
            UpdateItemCommand aec = commit.operation();
            Item i = new Item(aec.getControll(), aec.getKey(), aec.getValue());
            System.out.println(aec.toString());
            return Boolean.TRUE;
        } finally {
            commit.close();
        }
    }
    @SuppressWarnings("unused")
    public Boolean DeleteItemFila(Commit<DeleteItemCommand> commit) {
        try {
            DeleteItemCommand aec = commit.operation();
            Item i = new Item(aec.getControll(), aec.getKey(), null);
            System.out.println(aec.toString());
            return Boolean.TRUE;
        } finally {
            commit.close();
        }
    }

    @SuppressWarnings("unused")
    public String GetItemFila(Commit<ReadItemQuery> commit) {
        try {
            ReadItemQuery aec = commit.operation();
            System.out.println(aec.toString());
            return "DEU BOM";
        } finally {
            commit.close();
        }
    }

    public static void main(String[] args) {
        int myId = Integer.parseInt(args[0]);
        List<Address> addresses = new LinkedList<>();

        for (int i = 1; i < args.length; i += 2) {
            Address address = new Address(args[i], Integer.parseInt(args[i + 1]));
            addresses.add(address);
        }

        CopycatServer.Builder builder = CopycatServer.builder(addresses.get(myId))
                .withStateMachine(GraphStateMachine::new)
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                .withStorage(Storage.builder()
                        .withDirectory(new File("logs_" + myId)) //Must be unique
                        .withStorageLevel(StorageLevel.DISK)
                        .build());
        CopycatServer server = builder.build();

        if (myId == 0) {
            server.bootstrap().join();
        } else {
            server.join(addresses).join();
        }
    }
}
