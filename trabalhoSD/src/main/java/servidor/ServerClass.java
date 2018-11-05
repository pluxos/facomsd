package servidor;

import java.util.concurrent.Semaphore;

import com.stackleader.training.grpc.helloworld.api.GreeterGrpc;
import com.stackleader.training.grpc.helloworld.api.Reply;
import com.stackleader.training.grpc.helloworld.api.Request;

import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;
import servidor.dataBase.Data;
import servidor.dataBase.RecoveryData;
import servidor.queue.QueueCommand;

public class ServerClass extends GreeterGrpc.GreeterImplBase implements BindableService {
  public static Semaphore mutex_f1 = new Semaphore(1);
  public static Semaphore mutex = new Semaphore(1);
  private QueueCommand queueCommand = null;
  private Queue queue = null;
  private Data dataBase;
  
  public ServerClass() {
    try {
      System.out.println("iniciando serverClass");
      dataBase = new Data();
      // serverSocket = new ServerSocket(Constant.SERVER_PORT);
      queueCommand = new QueueCommand();
      queue = new Queue(queueCommand);
      queue.run();
      RecoveryData recovery = new RecoveryData();
      recovery.recovery(dataBase);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void send(Request req, StreamObserver<Reply> responseObserver) {
    System.out.println("iniciando thread cliente");
    HandlerThreadServer h = new HandlerThreadServer(queueCommand, dataBase, req, responseObserver);
    h.run();
    System.out.println("thread finalizada");
    try {
      h.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}