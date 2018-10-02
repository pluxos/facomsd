package servidor;

public class ServerApp {
  static Server server;
  
  public static void main(String[] args) {
    server = new Server();
    server.iniciar();
  }
  
  public static void iniciar() {
    server = new Server();
    server.iniciar();
  }
  
  public static void stop() {
    server.stop();
  }
}
