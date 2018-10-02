package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import servidor.dataBase.Data;
import servidor.menu.Menu;
import servidor.queue.QueueCommand;

public class HandlerThreadServer extends Thread {
  private String comando;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private QueueCommand queueCommand;
  private ClientData clientComand;
  private Data data;
  
  public HandlerThreadServer(Socket socket, QueueCommand queueCommand, Data data) {
    try {
      this.out = new ObjectOutputStream(socket.getOutputStream());
      this.in = new ObjectInputStream(socket.getInputStream());
      this.queueCommand = queueCommand;
      this.data = data;
      System.out.println(">>>>> Cliente conectado");
      out.writeObject(Menu.operacoes);
    } catch (Exception e) {
    }
  }
  
  public void run() {
    try {
      while (true) {
        clientComand = new ClientData();
        comando = (String) in.readObject();
        System.out.println(">>>>> Recebido: " + comando);
        if (HandlerCommandClient.checkComand(comando)) {
          clientComand.setData(data);
          clientComand.setComando(comando);
          clientComand.setOut(out);
          queueCommand.produceF1(clientComand);
        }
        else {
          out.writeObject("Syntaxe ou comando incorreto!");
        }
      }
    } catch (Exception e) {
    }
  }
}
