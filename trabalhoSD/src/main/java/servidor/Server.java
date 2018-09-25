package servidor;

import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

import servidor.dataBase.Data;
import servidor.queue.Queue;
import servidor.queue.QueueCommand;
import utils.Constant;

public class Server {
  public static Semaphore mutex_f1 = new Semaphore(1);
  public static Semaphore mutex = new Semaphore(1);
  private ServerSocket serverSocket = null;
  private QueueCommand queueCommand = null;
  private Queue queue = null;
  private boolean running = true;
  
  public void iniciar() {
    try {
      serverSocket = new ServerSocket(Constant.SERVER_PORT);
      queueCommand = new QueueCommand();
      Data.recovery();
      queue = new Queue(queueCommand);
      queue.run();
      while (running) {
        if (!running)
          return;
        new HandlerThreadServer(serverSocket.accept(), queueCommand).start();
      }
      System.out.println("server finalizado");
    } catch (Exception e) {
    }
  }
  
  public void stop() {
    this.running = false;
  }
}