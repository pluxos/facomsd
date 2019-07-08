package br.ufu.sd.main;

import br.ufu.sd.client.MenuCliente;

public class Client {

	public static void main(String[] args) {
		
		MenuCliente client = new MenuCliente(args[0], Integer.parseInt(args[1]));
		client.startClient();

		System.exit(0);
	}
}