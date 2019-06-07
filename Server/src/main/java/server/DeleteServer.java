package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import server.ChordGrpc.ChordImplBase;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.Table;

public class DeleteServer implements Runnable {
  private static final Logger logger = Logger.getLogger(DeleteServer.class.getName());
  public int serverKey;

  public Table table;

  private final ManagedChannel channel;
  private final ChordGrpc.ChordBlockingStub blockingStub; // Descobri que vc faz um blocking stub pra cada serviço
  
  public DeleteServer(String serverIP, int serverPort, int serverKey) {
    this(ManagedChannelBuilder.forAddress(serverIP, serverPort).usePlaintext().build());
    this.serverKey = serverKey;
    this.table = Table.getInstance();
  }

  DeleteServer(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = ChordGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
  }

  public void run() {
    // logger.info("Tentando enviar " + key + " -" + port + " - " + ip + " ...");
    DelRequest request = DelRequest.newBuilder()
                                         .setKey(serverKey)
                                         .build();

    DelResponse response;

    try {
      response = this.blockingStub.deleteServidor(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }

    try {
      this.shutdown();
    } catch (InterruptedException e) {
      System.out.println( e );
    }
  }

}