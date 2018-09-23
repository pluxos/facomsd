package servidor;

import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

public class Server {
	//static Map<BigInteger, String> dados ;
	static Semaphore mutex = new Semaphore(1);

  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    //dados = new HashMap<BigInteger, String>();
    
    //Data.recovery(dados);
    try {
      serverSocket = new ServerSocket(9876);
      while (true) {
        new DadosHandlerThread(serverSocket.accept()).start();
      }
    } catch (Exception e) {
    }
  }
}