package client;

import java.io.PrintStream;

public class ClientCommands implements Runnable {

	private PrintStream output;
	
	public ClientCommands(PrintStream output){
		this.output = output;
	}
	
	@Override
	public void run() {
		System.out.println("Thread de comando Iniciado!");
	}

}
