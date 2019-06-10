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

public class Create implements Runnable {
  private static final Logger logger = Logger.getLogger(Create.class.getName());
  public ByteString key;
  public ByteString value;  
  public int keysize;
  public int valuesize;
  public boolean client;
  private final ManagedChannel channel;
  private final CrudGrpc.CrudBlockingStub blockingStub;

  private StreamObserver<CreateResponse> responseC = null;

  public Create(String host, int port, BigInteger chave, byte[] value, boolean client) {
      this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
      byte[] variavel = chave.toByteArray();
      this.keysize = variavel.length;
      this.valuesize = value.length;
      this.key = ByteString.copyFrom(variavel);
      this.value = ByteString.copyFrom(value);
      this.client = client;
  }

  public Create(String host, int port, BigInteger chave, byte[] value, StreamObserver<CreateResponse> responseC) {
      this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
      byte[] variavel = chave.toByteArray();
      this.keysize = variavel.length;
      this.valuesize = value.length;
      this.key = ByteString.copyFrom(variavel);
      this.value = ByteString.copyFrom(value);
      this.responseC = responseC;
      this.client = false;  
    }

  Create(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = CrudGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
  }

  public void run() {
    logger.info("Tentando enviar " + this.key + " ...");
    CreateRequest request = CreateRequest.newBuilder()
                                         .setKey(this.key)
                                         .setValue(this.value)
                                         .setKeysize(this.keysize)
                                         .setValuesize(this.valuesize)
                                         .setClient(this.client)
                                         .build();
    CreateResponse response;
    try {
      response = blockingStub.create(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }

    if(this.responseC != null) {
      // responder para o observer
      this.responseC.onNext(response);
      this.responseC.onCompleted();
    }
    else {
      if (response.getRetorno() == true) {
        logger.info("Dado inserido com sucesso!");
      } else {
        logger.info("A chave especificada já existe no banco de dados!");
      }
    }

    try {
      this.shutdown();
    } catch (InterruptedException e) {
      System.out.println( e );
    }
  }

}