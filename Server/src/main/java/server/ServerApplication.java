package server;

import io.grpc.Context;
import server.receptor.ServerThread;

public class ServerApplication {

	public static void main(String[] args) {
		Thread server = new Thread(new ServerThread(args));
		Context.current().fork().run(server);
	}
}