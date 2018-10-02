package cliente;

public class ClientApp {
  public static void main(String args[]) {
    try {
      Client cliente = new Client();
      cliente.start();
    } catch (Exception e) {
      System.out.println("Erro ao executar cliente!!!");
    }
  }
}
