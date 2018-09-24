package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import servidor.menu.Menu;
import servidor.queue.QueueCommand;

public class HandlerThreadServer extends Thread {
  private String comando;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private QueueCommand queueCommand;
  private ClientData clientComand;
  
  public HandlerThreadServer(Socket socket, QueueCommand queueCommand) {
    try {
      this.clientComand = new ClientData();
      this.out = new ObjectOutputStream(socket.getOutputStream());
      this.in = new ObjectInputStream(socket.getInputStream());
      this.queueCommand = queueCommand;
      System.out.println("Cliente conectado");
      out.writeObject(Menu.operacoes);
    } catch (Exception e) {
    }
  }
  
  public void run() {
    try {
      while (true) {
        comando = (String) in.readObject();
        System.out.println("Recebido: " + comando);
        if (HandlerCommandClient.checkComand(comando)) {
          clientComand.setComando(comando);
          clientComand.setOut(out);
          queueCommand.produceF1(clientComand);
        }
        else {
          out.writeObject("Syntaxe ou comando incorreto!");
        }
      }
      // out.close();
      // in.close();
      // socket.close();
    } catch (Exception e) {
    }
  }
}
