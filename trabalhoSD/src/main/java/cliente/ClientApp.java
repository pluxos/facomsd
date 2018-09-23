package cliente;

public class ClientApp {
	
	public static void main(String args[]) {
		try {
			Thread cliente = new Thread(new Client());
			cliente.start();
			cliente.join();
		} catch (Exception e) {
		}
	}
}
