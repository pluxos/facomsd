package client.commons.utils;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class SocketConnection {

	public static Socket getSocket() throws IOException {
		Properties properties = FileUtils.getConfigProperties();
		String host = properties.getProperty("server.host");
		Integer port = Integer.parseInt(properties.getProperty("server.host.port"));
		return new Socket(host, port);
	}
}