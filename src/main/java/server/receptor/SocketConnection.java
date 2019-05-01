package server.receptor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

import client.commons.utils.FileUtils;

public class SocketConnection {

	public static ServerSocket getServerSocket() throws IOException {
		Properties properties = FileUtils.getConfigProperties();
		Integer port = Integer.parseInt(properties.getProperty("server.host.port"));
		return new ServerSocket(port);
	}
}