package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
  private static void recovery(Map<Long, Dados> dados) {
    dados.put(1L, new Dados(1, "Teste1"));
    dados.put(2L, new Dados(2, "Teste2"));
    dados.put(3L, new Dados(3, "Teste3"));
    dados.put(4L, new Dados(4, "Teste4"));
  }
  
  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    Map<Long, Dados> dados = new HashMap<Long, Dados>();
    recovery(dados);
    try {
      serverSocket = new ServerSocket(9876);
      while (true) {
        new DadosHandlerThread(serverSocket.accept(), dados).start();
      }
    } catch (Exception e) {
    }
  }
}