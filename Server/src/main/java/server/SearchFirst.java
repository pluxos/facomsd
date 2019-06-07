package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import server.ChordGrpc.ChordImplBase;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import singletons.*;

import server.Table;

public class SearchFirst implements Runnable {
  private static final Logger logger = Logger.getLogger(SearchFirst.class.getName());
  public Table table;
  public ItemFila itemfila = null;

  public StreamObserver<SearchResponse> responseObserver = null;

  private final ManagedChannel channel;
  private final ChordGrpc.ChordBlockingStub blockingStub; // Descobri que vc faz um blocking stub pra cada serviço

  public SearchFirst(String serverIP, int serverPort, ItemFila itemfila) {
    this(ManagedChannelBuilder.forAddress(serverIP, serverPort).usePlaintext().build());
    this.table = Table.getInstance();
    this.itemfila = itemfila;
  }

  public SearchFirst(String serverIP, int serverPort, StreamObserver<SearchResponse> responseObserver) {
    this(ManagedChannelBuilder.forAddress(serverIP, serverPort).usePlaintext().build());
    this.table = Table.getInstance();
    this.responseObserver = responseObserver;
  }

  SearchFirst(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = ChordGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
  }

  public void run() {
    // logger.info("Tentando enviar " + key + " -" + port + " - " + ip + " ...");
    SearchRequest request = SearchRequest.newBuilder().build();

    SearchResponse response;
    try {
      response = this.blockingStub.searchFirst(request); // O nome da função no client e o nome no blockingStub não
                                                         // precisam ser iguais
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }

    String responseIP = response.getIp();
    int responsePort = response.getPort();

    if (this.responseObserver != null) {
      SearchResponse originalResponse = SearchResponse.newBuilder().setIp(responseIP).setPort(responsePort).build();
      responseObserver.onNext(originalResponse);
      responseObserver.onCompleted();
    } else {
      if (this.itemfila == null) {
        System.out.println("nao devia entrar aqui");
      } else {
        if ( responseIP == table.myIP && responsePort == table.myPort){
          try {
              if( BigInteger.valueOf(table.myKey).compareTo( new BigInteger(this.itemfila.getKey()) ) < 0 ) {
                this.itemfila.ourResponsability = false;
              }
              F1.getInstance().put(itemfila);
          } catch (Exception e) {
              System.out.println("Erro: " + e.getMessage());
          }
        } else {
          // crud para o responseIP
          String command = this.itemfila.getControll();
          
          if( command.equals( "CREATE" ) ) {            
            new Thread( new Create(responseIP, responsePort, new BigInteger(this.itemfila.getKey()),  this.itemfila.getValue()) ).start();
          }

          if( command.equals( "UPDATE" ) ) {
            new Thread( new Update(responseIP, responsePort, new BigInteger(this.itemfila.getKey()),  this.itemfila.getValue()) ).start();
          }
          
          if( command.equals( "READ" ) ) {
            new Thread( new Read(responseIP, responsePort, new BigInteger(this.itemfila.getKey())) ).start();
          }
          
          if( command.equals( "DELETE" ) ) {
            new Thread( new Delete(responseIP, responsePort, new BigInteger(this.itemfila.getKey())) ).start();
          }

        }
      }
    }

    // logger.info("SEARCH FIRST = " + "IP: " + response.getIp() + " PORT: " +
    // response.getPort() + " KEY" + response.getKey());
    try {
      this.shutdown();
    } catch (InterruptedException e) {
      System.out.println(e);
    }
  }

}