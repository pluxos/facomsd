package server;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.LinkedList;
import java.util.Properties;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;
import server.query.UpdateQuery;
import server.query.DeleteQuery;
import server.query.ReadQuery;
import server.query.CreateQuery;

class Server extends StateMachine {

  public static void main(String[] args) {
    try {
      Properties properties = new Properties();
      FileInputStream propsFS = new FileInputStream("src/main/resources/Constants.prop");
      properties.load(propsFS);
      Integer port = Integer.parseInt(properties.getProperty("port"));

      new Thread(new Persistence()).start();
      new Thread(new Logger()).start();
      new Thread(new Consumidor()).start();

      int myId = Integer.parseInt(args[0]);
      List<Address> addresses = new LinkedList<>();

      for (int i = 1; i < args.length; i += 2) {
        Address address = new Address(args[i], Integer.parseInt(args[i + 1]));
        addresses.add(address);
      }

      CopycatServer.Builder builder = CopycatServer.builder(addresses.get(myId))
          /* ver se procece */ .withStateMachine(Server::new)
          .withTransport(NettyTransport.builder().withThreads(4).build())
          .withStorage(Storage.builder().withDirectory(new File("logs_" + myId)) // Must be unique
              .withStorageLevel(StorageLevel.DISK).build());
      CopycatServer server = builder.build();

      if (myId == 0) {
        server.bootstrap().join();
      } else {
        server.join(addresses).join();
      }
    } catch (Exception e) {
      System.out.println("Erro: " + e.getMessage());
    }
  }

  public void create(Commit<CreateQuery> commit) {
    EntryPoint.create(commit);
  }

  public void read(Commit<ReadQuery> commit) {
    EntryPoint.read(commit);
  }

  public void update(Commit<UpdateQuery> commit) {
    EntryPoint.update(commit);
  }

  public void delete(Commit<DeleteQuery> commit) {
    EntryPoint.delete(commit);
  }
}