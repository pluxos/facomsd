package server;

import server.controller.ServerThread;

public class ServerApplication {
	
	private static String defaultFilePath = "comand.log";

	public static void main(String[] args) {
		Thread server = new Thread(new ServerThread(args.length > 0 ? args[0] : defaultFilePath));
		server.start();
	}
}