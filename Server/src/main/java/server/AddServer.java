package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import server.ChordGrpc.ChordImplBase;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Integer;

import server.Table;

public class AddServer implements Runnable {
  private static final Logger logger = Logger.getLogger(AddServer.class.getName());
  public int newKey;
  public String newIP;
  public int newPort;

  public Table table;

  public StreamObserver<AddResponse> responseObserver = null;

  private final ManagedChannel channel;
  private final ChordGrpc.ChordBlockingStub blockingStub; // Descobri que vc faz um blocking stub pra cada serviço
  

  public AddServer(String serverIP, int serverPort, int newKey, String newIP, int newPort, Table table) {
    this(ManagedChannelBuilder.forAddress(serverIP, serverPort).usePlaintext().build());
    this.newKey = newKey;
    this.newIP = newIP;
    this.newPort = newPort;
    this.table = table;
  }

  public AddServer(String serverIP, int serverPort, int newKey, String newIP, int newPort, Table table, StreamObserver<AddResponse> responseObserver) {
    this(ManagedChannelBuilder.forAddress(serverIP, serverPort).usePlaintext().build());
    this.newKey = newKey;
    this.newIP = newIP;
    this.newPort = newPort;
    this.table = table;
    this.responseObserver = responseObserver;
  }

  AddServer(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = ChordGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
  }

  public void run() {
    // logger.info("Tentando enviar " + key + " -" + port + " - " + ip + " ...");
    AddRequest request = AddRequest.newBuilder()
                                         .setKey(newKey)
                                         .setPort(newPort)
                                         .setIp(newIP).
                                         build();
    AddResponse response;

    try {
      response = this.blockingStub.addServidor(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }

    int responseKey = response.getKey();
    String responseIP = response.getIp();
    int responsePort = response.getPort();

    // if (responseKey == -1 && responsePort == -1 && responseIP.equals("NEXT")) {
    //   // IGNORAR A RESPOSTA
    //   try {
    //     this.shutdown();
    //   } catch (InterruptedException e) {
    //     System.out.println( e );
    //   }
    // }

    if (responseKey == 0 && responsePort == 0 && responseIP.equals("ERROR")) {
      // SERVIDOR JÁ EXISTENTE
      System.out.println("SERVIDOR JÁ EXISTENTE");
      try {
        this.shutdown();
      } catch (InterruptedException e) {
        System.out.println( e );
      }
    }

    if (this.responseObserver != null) {
      AddResponse originalResponse = AddResponse.newBuilder().setIp(responseIP).setPort(responsePort).setKey(responseKey).build();
      responseObserver.onNext(originalResponse);
      responseObserver.onCompleted();
    }
    else {
      // logger.info("Servidor Inserido " + "IP: " + response.getIp() + " PORT: " + response.getPort() + " KEY" + response.getKey());
      this.table.insertTable(response.getKey(), response.getIp(), response.getPort(), 0);
      for (int i = 1; i < this.table.table.length; i++) {
        
        int expKeyX = Integer.parseInt(this.table.table[i][0]);
        if (newKey > response.getKey()) {
          expKeyX = expKeyX - newKey;
        }
        new Thread( new UpdateServer(response.getIp(), response.getPort(), this.table.myKey, expKeyX, i) ).start();
      }
      new Thread( new NotifyAllServers(response.getIp(), response.getPort(), newKey, newIP, newPort) ).start();
    }
    try {
      this.shutdown();
    } catch (InterruptedException e) {
      System.out.println( e );
    }
  }

}