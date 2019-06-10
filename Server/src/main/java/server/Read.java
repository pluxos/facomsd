package server;

import java.math.BigInteger;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.protobuf.ByteString;


public class Read implements Runnable {
  private static final Logger logger = Logger.getLogger(Read.class.getName());
  public ByteString key;
  public int keysize;
  public boolean client;
  private final ManagedChannel channel;
  private final CrudGrpc.CrudBlockingStub blockingStub;

  private StreamObserver<ReadResponse> responseR = null;

  public Read(String host, int port, BigInteger key, boolean client) {
    this(ManagedChannelBuilder.forAddress(host, port)
    .usePlaintext()
    .build());
    byte[] variavel = key.toByteArray();
    this.keysize = variavel.length;
    this.key = ByteString.copyFrom(variavel);
    this.client = client;
  }

  public Read(String host, int port, BigInteger key, StreamObserver<ReadResponse> responseR) {
    this(ManagedChannelBuilder.forAddress(host, port)
    .usePlaintext()
    .build());
    byte[] variavel = key.toByteArray();
    this.keysize = variavel.length;
    this.key = ByteString.copyFrom(variavel);
    this.responseR = responseR;
    this.client = false;  
  }

  Read(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = CrudGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public void run() {
    logger.info("Procurando o elemento de chave: " + key + " ...");
    ReadRequest request = ReadRequest.newBuilder().setKey(key).setKeysize(this.keysize).setClient(this.client).build();
    ReadResponse response;
    try {
      response = blockingStub.read(request);
    } catch (StatusRuntimeException e) {
      logger.info("Não existe a chave especificada!");
      return;
    }

    if(this.responseR != null) {
      // responder para o observer
      this.responseR.onNext(response);
      this.responseR.onCompleted();
    }
    else {
      if(response.getValue() != null) {
        byte[] valor = new byte[response.getValuesize()];
        response.getValue().copyTo(valor, 0);
        logger.info("Recebido o valor: " + new String(valor));
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