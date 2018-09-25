package facomSD.facom.com;

import servidor.Server;

public class ThreadStartServer implements Runnable {
  Server s = new Server();
  
  public void run() {
    s.iniciar();
  }
}
