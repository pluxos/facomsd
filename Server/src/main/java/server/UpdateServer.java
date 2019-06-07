package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import server.ChordGrpc.ChordImplBase;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.Table;

public class UpdateServer implements Runnable {
  private static final Logger logger = Logger.getLogger(UpdateServer.class.getName());
  public int serverKey;
  public int expKey;
  public int position;

  public Table table;

  public StreamObserver<UpResponse> responseObserver = null;

  private final ManagedChannel channel;
  private final ChordGrpc.ChordBlockingStub blockingStub; // Descobri que vc faz um blocking stub pra cada serviço
  

  public UpdateServer(String serverIP, int serverPort, int serverKey, int expKey, int position) {
    this(ManagedChannelBuilder.forAddress(serverIP, serverPort).usePlaintext().build());
    this.serverKey = serverKey;
    this.expKey = expKey;
    this.position = position;
    this.table = Table.getInstance();
  }

  public UpdateServer(String serverIP, int serverPort, int serverKey, int expKey, int position, StreamObserver<UpResponse> responseObserver) {
    this(ManagedChannelBuilder.forAddress(serverIP, serverPort).usePlaintext().build());
    this.serverKey = serverKey;
    this.expKey = expKey;
    this.position = position;
    this.table = Table.getInstance();
    this.responseObserver = responseObserver;
  }

  UpdateServer(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = ChordGrpc.newBlockingStub(channel);
  }
  
  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
  }

  public void run() {
    // logger.info("Tentando enviar " + key + " -" + port + " - " + ip + " - " + expkey + " ...");
    UpRequest request = UpRequest.newBuilder()
                                         .setMykey(serverKey)
                                         .setPosition(position)
                                         .setExpkey(expKey).
                                         build();
    UpResponse response;

    try {
      response = this.blockingStub.updateServidor(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }

    int responseKey = response.getKey();
    String responseIP = response.getIp();
    int responsePort = response.getPort();

    if (responseKey == 0 && responsePort == 0 && responseIP.equals("ERROR")) {
      // NAO ACHOU CHAVE
      System.out.println("NÃO ACHOU CHAVE CANDIDATE");
      try {
        this.shutdown();
      } catch (InterruptedException e) {
        System.out.println( e );
      }
    }
    
    if (this.responseObserver != null) {
      UpResponse originalResponse = UpResponse.newBuilder().setIp(responseIP).setPort(responsePort).setKey(responseKey).build();
      responseObserver.onNext(originalResponse);
      responseObserver.onCompleted();
    }
    else {
      // logger.info("Servidor Atualizado " + "IP: " + response.getIp() + " PORT: " + response.getPort() + " KEY" + response.getKey());
      this.table.insertTable(response.getKey(), response.getIp(), response.getPort(), position);
    }
    try {
      this.shutdown();
    } catch (InterruptedException e) {
      System.out.println( e );
    }
  }

}