package server;

import server.controller.ServerThread;

public class ServerApplication {

	public static void main(String[] args) {
		Thread server = new Thread(new ServerThread(args));
		server.start();
	}
}