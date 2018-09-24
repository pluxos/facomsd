package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import utils.Constant;

public class Client {
  static Semaphore mutex = new Semaphore(1);
  private ClientResponse clientResponse;
  private CommandHandler commandHandler;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private Thread tRead;
  private Thread tResponse;
 private Scanner s;
 private Socket socket;
 private String menu="";
  
  public ObjectOutputStream getObjectOutputStream() {
    return out;
  }
  
  public ObjectInputStream getObjectInputStream() {
    return in;
  }
  
  public void init() throws IOException, ClassNotFoundException {
    s = new Scanner(System.in);
    socket = new Socket(Constant.HOST, Constant.SERVER_PORT);
    out = new ObjectOutputStream(socket.getOutputStream());
    in = new ObjectInputStream(socket.getInputStream());
    menu = (String) in.readObject();
    commandHandler = new CommandHandler(menu);
  }
  
  public void start() {
    try {
      init();
      tRead = new Thread(new ClientSend(socket, s, out, menu, commandHandler));
      tRead.start();
      clientResponse = new ClientResponse(in);
      tResponse = new Thread(clientResponse);
      tResponse.start();
      while (true) {
        if (!tRead.isAlive()) {
          Thread.sleep(5000);
          clientResponse.finalizar();
          break;
        }
      }
      System.out.println("ClientResponse finalizado");
      System.exit(0);
    } catch (Exception e) {
    }
  }
}
