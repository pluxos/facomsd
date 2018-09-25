package servidor.queue;

import java.io.IOException;
import java.io.ObjectOutputStream;

import servidor.ClientData;
import servidor.Server;
import servidor.command.ExecuteCommand;

public class QueueF3 extends Queue implements Runnable {
  private ObjectOutputStream out;
  
  public QueueF3(QueueCommand queue) {
    super(queue);
  }
  
  ExecuteCommand execute = new ExecuteCommand();
  
  public void erro() {
    try {
      out.writeObject("Erro ao pocessar comando");
    } catch (IOException e) {
      System.out.println("Erro ao enviar resposta");
    }
  }
  
  @Override
  public void run() {
    try {
      System.out.println("Iniciando F3");
      while (true) {
        Server.mutex.acquire();
        ClientData elemento =  super.queue.consumeF3();
        out = elemento.getOut();
        String resposta = execute.execute(elemento.getComando());
        System.out.println("comando de F3 executado");
        elemento.getOut().writeObject(resposta);
      }
    } catch (InterruptedException e) {
      erro();
    } catch (Exception e) {
      erro();
    }
  }
}
