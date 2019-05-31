package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.protobuf.ByteString;

public class Delete implements Runnable {
  private static final Logger logger = Logger.getLogger(Delete.class.getName());
    public ByteString key;
    public int keysize;
    private final ManagedChannel channel;
    private final CrudGrpc.CrudBlockingStub blockingStub;
  
    public Delete(String host, int port, BigInteger key) {
      this(ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build());
      this.keysize = key.toByteArray().length;
      this.key = ByteString.copyFrom(key.toByteArray());
    }
  
    Delete(ManagedChannel channel) {
      this.channel = channel;
      blockingStub = CrudGrpc.newBlockingStub(channel);
    }
  
    public void shutdown() throws InterruptedException {
      channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
  
    public void run() {
        logger.info("Removendo elemento de chave: " + key + " ...");
        DeleteRequest request = DeleteRequest.newBuilder()
                                             .setKey(key)
                                             .setKeysize(this.keysize)
                                             .build();
        DeleteResponse response;
        try {
          response = blockingStub.delete(request);
        } catch (StatusRuntimeException e) {
         logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
         return;
        }
        if (response.getRetorno() == true) {
          logger.info("O elemento foi removido com sucesso!");
        } else {
          logger.info("Não existe a chave especificada!");
        }
        try {
          this.shutdown();
        } catch (InterruptedException e) {
          System.out.println( e );
        }
    }

}