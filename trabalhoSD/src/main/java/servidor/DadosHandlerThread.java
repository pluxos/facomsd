package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class DadosHandlerThread extends Thread {
  private Socket socket = null;
  Map<Long, Dados> dados;
  
  public DadosHandlerThread(Socket socket, Map<Long, Dados> dados) {
    this.socket = socket;
    this.dados = dados;
  }
  
  public void run() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      Long key = (Long) in.readObject();
      while (key.longValue() != 0L) {
        System.out.println("key " + key + " recebida");
        if (dados.containsKey(key)) {
          out.writeObject(dados.get(key));
        }
        else {
          out.writeObject(new Dados(-1, ""));
        }
        key = (Long) in.readObject();
      }
      out.close();
      in.close();
      socket.close();
    } catch (Exception e) {
    }
  }
}
