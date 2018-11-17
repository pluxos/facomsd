package servidor;

import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.stub.StreamObserver;
import servidor.dataBase.Data;
import servidor.queue.QueueCommand;

public class HandlerThreadServer extends Thread {
  private String comando;
  private QueueCommand queueCommand;
  private ClientData clientComand;
//  private Data data;
  private Request req;
  StreamObserver<Reply> responseObserver;
  
  public HandlerThreadServer(QueueCommand queueCommand, Request req, StreamObserver<Reply> responseObserver) {
    try {
      this.responseObserver = responseObserver;
      this.queueCommand = queueCommand;
//      this.data = data;
      this.req = req;
//      System.out.println(">>>>> Cliente conectado");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void run() {
    try {
      clientComand = new ClientData();
      comando = this.req.getName();
      System.out.println(">>> Recebido: " + comando);
      if (HandlerCommandClient.checkComand(comando)) {
//        clientComand.setData(data);
        clientComand.setComando(comando);
        clientComand.setOut(responseObserver);
        queueCommand.produceF1(clientComand);
      }
      else {
        System.out.println("Syntaxe ou comando incorreto!");
        clientComand.setOut(responseObserver);
        clientComand.sendReply("Syntaxe ou comando incorreto!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
