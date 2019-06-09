package server;

import server.receptor.ServerThread;

public class ServerApplication {

	public static void main(String[] args) {
		Thread server = new Thread(new ServerThread(args));
		server.start();
	}
}