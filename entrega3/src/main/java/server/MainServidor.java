package server;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import command.*;
import type.*;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

public class MainServidor extends StateMachine
{
    private static BlockingQueue<Item> F1 = new LinkedBlockingDeque<>();
    private static BlockingQueue<Item> F2 = new LinkedBlockingDeque<>();
    private static BlockingQueue<Item> F3 = new LinkedBlockingDeque<>();

    public DataBase banco = new DataBase();
    public Comunication con = new Comunication();

    public static void main(String[] args) 
    {
        int myId = Integer.parseInt(args[0]);
        List<Address> addresses = new LinkedList<>();

        for(int i = 1; i <args.length; i+=2)
    	{
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
    		addresses.add(address);
        }
        
        CopycatServer.Builder builder = CopycatServer.builder(addresses.get(myId))
                                                     .withStateMachine(GraphStateMachine::new)
                                                     .withTransport( NettyTransport.builder()
                                                                     .withThreads(4)
                                                                     .build())
                                                     .withStorage( Storage.builder()
                                                                   .withDirectory(new File("logs_"+myId))
                                                                   .withStorageLevel(StorageLevel.DISK)
                                                                   .build());
        CopycatServer server = builder.build();

        if(myId == 0)
        {
            System.out.println("Iniciando servidor...");
            server.bootstrap().join();
        }
        else
        {
            System.out.println("Reiniciando servidor...");
            server.join(addresses).join();
        }
    }

    public void Create(Commit<CreateCommand> commit)
    {
        try
        {
            CreateCommand create = commit.operation();
            Operation operation = new Operation("CREATE", create.id, new String(create.value, "UTF-8"));
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void Read(Commit<ReadCommand> commit)
    {
        try
        {
            ReadCommand read = commit.operation();
            Operation operation = new Operation("READ", read.id);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void Delete(Commit<DeleteCommand> commit)
    {
        try
        {
            DeleteCommand delete = commit.operation();
            Operation operation = new Operation("DELETE", delete.id);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void Update(Commit<UpdateCommand> commit)
    {
        try
        {
            UpdateCommand update = commit.operation();
            Operation operation = new Operation("UPDATE", update.id, new String(update.value, "UTF-8"));
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
