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
    private final Kv database = new Kv();
    @SuppressWarnings("unused")
    public String AddItemFila(Commit<CreateItemCommand> commit) {
        CreateItemCommand aec = null;
        try {
            aec =  commit.operation();
            F1.getInstance().put(new Item(aec.getControll(),aec.getKey(),aec.getValue()));
            System.out.println(commit.operation().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            commit.close();
        }
        return database.Insert( aec.getKey(), aec.getValue() )?  "CREATE SUCESS!!" : "CREATE FAIL!!";
    }
    @SuppressWarnings("unused")
    public String UpdateItemFila(Commit<UpdateItemCommand> commit) {
            UpdateItemCommand aec =  null;
        try {
            aec =  commit.operation();
            F1.getInstance().put(new Item(aec.getControll(),aec.getKey(),aec.getValue()));
            System.out.println(commit.operation().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            commit.close();
        }
            return database.Update( aec.getKey(), aec.getValue() ) ?  "UPDATE SUCESS!!" : "UPDATE FAIL!!";
    }
    @SuppressWarnings("unused")
    public String DeleteItemFila(Commit<DeleteItemCommand> commit) {
        DeleteItemCommand aec = null;
        try {
            aec =  commit.operation();
            F1.getInstance().put(new Item(aec.getControll(),aec.getKey(), null ));
            System.out.println(commit.operation().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            commit.close();
        }
        return  database.Delete( aec.getKey() ) ?  "DELETE SUCESS!!" : "DELETE FAIL!!";
    }

    @SuppressWarnings("unused")
    public String GetItemFila(Commit<ReadItemQuery> commit) {
            ReadItemQuery aec = null;
            String s= new String();
        try {
            aec =  commit.operation();
            F1.getInstance().put(new Item(aec.getControll(),aec.getKey(), null ));
            System.out.println(commit.operation().toString());
            s = database.Read( aec.getKey() );
            if ( s == null )
                return "READ FAIL!!";
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            commit.close();
        }
        return  s;
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
        new Thread(new Logger()).start();
        CopycatServer server = builder.build();
        new Thread(new Consumidor()).start();
        if (myId == 0) {
            server.bootstrap().join();
        } else {
            server.join(addresses).join();
        }
    }
}
