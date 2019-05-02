package server;


import server.controller.Server;

public class ServerApplication {

	public static void main(String[] args) {
		Thread server = new Thread(new Server());
		server.start();
	}
}