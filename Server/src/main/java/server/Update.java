package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Update implements Runnable {
  private static final Logger logger = Logger.getLogger(Update.class.getName());
  public int key;
  public String value;  
  private final ManagedChannel channel;
  private final CrudGrpc.CrudBlockingStub blockingStub;

  public Update(String host, int port, int key, String value) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
      this.key = key;
      this.value = value;
  }

  Update(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = CrudGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public void run() {
    logger.info("Mudando elemento de chave: " + key + " para valor: " + value + " ...");
     UpdateRequest request = UpdateRequest.newBuilder().setKey(key)
                                                       .setValue(value)
                                                       .build();
     UpdateResponse response;
     try {
      response = blockingStub.update(request);
     } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
     }
     if (response.getRetorno() == true) {
      logger.info("O elemento foi alterado com sucesso!");
     } else {
       logger.info("Chave inexistente");
     }
  }
}