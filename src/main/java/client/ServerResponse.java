package client;

import java.util.Scanner;

public class ServerResponse implements Runnable {

	private Scanner input;

	public ServerResponse(Scanner input){
		this.input = input;
	}

	@Override
	public void run() {
		//System.out.println("Respostas do servidor startado!");
	}

}
