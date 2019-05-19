package server;

import server.controller.Server;

public class ServerApplication {
	
	private static String defaultFilePath = "comand.log";

	public static void main(String[] args) {
		Thread server = new Thread(new Server(args.length > 0 ? args[0] : defaultFilePath));
		server.start();
	}
}