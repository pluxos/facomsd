package client;

public class ClientCommands implements Runnable {

	private String test;
	
	public ClientCommands(String test){
		this.test = test;
	}
	
	@Override
	public void run() {
		System.out.println("Thread de comando Iniciado! " + this.test);
	}

}
