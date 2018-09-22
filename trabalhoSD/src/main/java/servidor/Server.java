package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    Map<Long, Dados> dados = new HashMap<Long, Dados>();
    
    Data.recovery(dados);
    try {
      serverSocket = new ServerSocket(9876);
      while (true) {
        new DadosHandlerThread(serverSocket.accept(), dados).start();
      }
    } catch (Exception e) {
    }
  }
}