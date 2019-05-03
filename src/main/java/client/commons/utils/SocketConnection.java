package client.commons.utils;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class SocketConnection {
	
	private static Socket socket;

	public static Socket getSocket() throws IOException {
		Properties properties = FileUtils.getConfigProperties();
		String host = properties.getProperty("server.host");
		Integer port = Integer.parseInt(properties.getProperty("server.host.port"));
		socket = new Socket(host, port);
		return socket;
	}
	
	public static boolean isAlive() {
		return socket.isClosed();
	}
}