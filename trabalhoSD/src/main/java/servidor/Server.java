package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

import servidor.dataBase.Data;
import servidor.dataBase.RecoveryData;
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
  private Data dataBase;
  
  public void iniciar() {
    try {
      dataBase = new Data();
      serverSocket = new ServerSocket(Constant.SERVER_PORT);
      queueCommand = new QueueCommand();
      queue = new Queue(queueCommand);
      queue.run();
      RecoveryData recovery = new RecoveryData();
      recovery.recovery(dataBase);
      while (running) {
        if (!running)
          return;
        new HandlerThreadServer(serverSocket.accept(), queueCommand, dataBase).start();
      }
      System.out.println(">>>>> server finalizado");
    } catch (Exception e) {
    }
  }
  
  public void stop() {
    try {
      System.out.println(">>>>> parando servidor");
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.running = false;
  }
}