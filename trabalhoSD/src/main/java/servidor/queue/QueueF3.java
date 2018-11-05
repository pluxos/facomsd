package servidor.queue;

import servidor.ClientData;
import servidor.ServerClass;
import servidor.command.ExecuteCommand;

public class QueueF3 extends Queue implements Runnable {
  
  
  public QueueF3(QueueCommand queue) {
    super(queue);
  }
  
  ExecuteCommand execute = new ExecuteCommand();
  

  
  @Override
  public void run() {
    try {
      System.out.println("Iniciando F3");
      while (true) {
        ServerClass.mutex.acquire();
        ClientData elemento = super.queue.consumeF3();
       // out = elemento.getOut();
        String resposta = execute.execute(elemento);
        System.out.println("resposta F3: "+resposta);
        elemento.sendReply(resposta);
       // elemento.getOut().writeObject(resposta);
      }
    } catch (InterruptedException e) {
     System.out.println("erro");
    } catch (Exception e) {
      System.out.println("erro");
    }
  }
}
