package servidor;

public class ServerApp {
  public static void main(String[] args) {
    HandlerServer s = new HandlerServer();
    try {
      s.activate();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
