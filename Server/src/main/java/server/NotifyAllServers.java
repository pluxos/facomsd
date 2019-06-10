package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import server.ChordGrpc.ChordImplBase;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.Table;

public class NotifyAllServers implements Runnable {
  private static final Logger logger = Logger.getLogger(NotifyAllServers.class.getName());
  public int newKey;
  public String newIP;
  public int newPort;

  public Table table;

  private final ManagedChannel channel;
  private final ChordGrpc.ChordBlockingStub blockingStub; // Descobri que vc faz um blocking stub pra cada serviço
  
  public NotifyAllServers(String serverIP, int serverPort, int newKey, String newIP, int newPort) {
    this(ManagedChannelBuilder.forAddress(serverIP, serverPort).usePlaintext().build());
    this.newKey = newKey;
    this.newIP = newIP;
    this.newPort = newPort;
    this.table = Table.getInstance();
  }

  NotifyAllServers(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = ChordGrpc.newBlockingStub(channel);
  }
  
  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
  }

  public void run() {
    NotifyRequest request = NotifyRequest.newBuilder()
                                         .setNewkey(newKey)
                                         .setNewport(newPort)
                                         .setNewip(newIP)
                                         .build();

    NotifyResponse response;

    try {
      response = this.blockingStub.notifyServidor(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }

    String responseOK = response.getOk();

    if (responseOK.equals("ERROR")) {
      // ???
      System.out.println("ALGUM ERRO");
    }

    try {
      this.shutdown();
    } catch (InterruptedException e) {
      System.out.println( e );
    }
  }

}