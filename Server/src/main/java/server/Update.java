package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.protobuf.ByteString;

public class Update implements Runnable {
  private static final Logger logger = Logger.getLogger(Update.class.getName());
  public ByteString key;
  public ByteString value;  
  public int keysize;
  public int valuesize;
  private final ManagedChannel channel;
  private final CrudGrpc.CrudBlockingStub blockingStub;

  public Update(String host, int port, BigInteger key, byte[] value) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    byte[] variavel = key.toByteArray();
    this.keysize = variavel.length;
    this.valuesize = value.length;
    this.key = ByteString.copyFrom(variavel);
    this.value = ByteString.copyFrom(value);
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
                                                       .setKeysize(this.keysize)
                                                       .setValuesize(this.valuesize)
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
     try {
      this.shutdown();
    } catch (InterruptedException e) {
      System.out.println( e );
    }
  }
}