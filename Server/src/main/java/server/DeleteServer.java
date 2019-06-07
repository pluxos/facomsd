package server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.protobuf.ByteString;

public class DeleteServer implements Runnable {
  private static final Logger logger = Logger.getLogger(DeleteServer.class.getName());
  public int key;
  public int port;
  public String ip;
  private final ManagedChannel channel;
  private final ChordGrpc.ChordBlockingStub blockingStub; // Descobri que vc faz um blocking stub pra cada serviço
  

  public DeleteServer(String host, int port, int key, int portM, String ip) {
      this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
      this.key = key;
      this.port = portM;
      this.ip = ip;
    }

  DeleteServer(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = ChordGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
  }

  public void run() {
    logger.info("Tentando enviar " + key + " -" + port + " - " + ip + " ...");
    DelRequest request = DelRequest.newBuilder()
                                         .setKey(key)
                                         .setPort(port)
                                         .setIp(ip).
                                         build();
    DelResponse response;
    try {
      response = this.blockingStub.deleteServidor(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }
    if (response.getRetorno() == true) {
      System.out.println("Servidor deletado com sucesso");
    }
    try {
      this.shutdown();
    } catch (InterruptedException e) {
      System.out.println( e );
    }
  }

}