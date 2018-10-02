package cliente;

import java.io.ObjectInputStream;

public class ClientResponse implements Runnable {
  private ObjectInputStream in;
  private boolean running;
  
  public ClientResponse(ObjectInputStream in) {
    this.in = in;
    this.running = true;
  }
  
  public void finalizar() {
    this.running = false;
  }
  
  public void run() {
    try {
      while (this.running) {
        String resposta = (String) in.readObject();
        System.out.println(">>>>> " + resposta + "\n\n");
        Client.mutex.release();
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}
