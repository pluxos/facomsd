package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.protobuf.ByteString;

public class Delete implements Runnable {
  private static final Logger logger = Logger.getLogger(Delete.class.getName());
  public ByteString key;
  public int keysize;
  public boolean client;
  private final ManagedChannel channel;
  private final CrudGrpc.CrudBlockingStub blockingStub;
  
  private StreamObserver<DeleteResponse> responseD = null;

  public Delete(String host, int port, BigInteger key,boolean client) {
    this(ManagedChannelBuilder.forAddress(host, port)
    .usePlaintext()
    .build());
    this.keysize = key.toByteArray().length;
    this.key = ByteString.copyFrom(key.toByteArray());
    this.client = client;
  }
  
  public Delete(String host, int port, BigInteger key, StreamObserver<DeleteResponse> responseD) {
    this(ManagedChannelBuilder.forAddress(host, port)
    .usePlaintext()
    .build());
    this.keysize = key.toByteArray().length;
    this.key = ByteString.copyFrom(key.toByteArray());
    this.responseD = responseD;
    this.client = false;  
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
                                          .setClient(this.client)
                                          .build();
    DeleteResponse response;
    try {
      response = blockingStub.delete(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }

    if(this.responseD != null) {
      // responder para o observer
      this.responseD.onNext(response);
      this.responseD.onCompleted();
    }
    else {
      if (response.getRetorno() == true) {
        logger.info("O elemento foi removido com sucesso!");
      } else {
        logger.info("Não existe a chave especificada!");
      }
    }

    try {
      this.shutdown();
    } catch (InterruptedException e) {
      System.out.println( e );
    }
  }

}