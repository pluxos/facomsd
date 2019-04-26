package client.business;

import java.util.Scanner;

public class ServerResponse implements Runnable {

	private Scanner input;

	public ServerResponse(Scanner input){
		this.input = input;
	}

	@Override
	public void run() {
		System.out.println("Respostas do servidor startado!");

		while(true){
			try {
				String res = this.input.nextLine();

				System.out.println(res);
			}catch (Exception e){
				System.out.println("TRETA");
				break;
			}
		}
	}

}
