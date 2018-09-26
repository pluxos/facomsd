package servidor.queue;

public class Queue {
  protected QueueCommand queue;
  
  public Queue(QueueCommand queue) {
    this.queue = queue;
  }
  
  public void run() throws InterruptedException {
    Thread f1 = new Thread(new QueueF1(queue));
    Thread f2 = new Thread(new QueueF2(queue));
    Thread f3 = new Thread(new QueueF3(queue));
    f1.start();
    f2.start();
    f3.start();
  }
}
