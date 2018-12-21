package br.com.configuration;

import java.io.File;

import br.com.file.FileReader;

public class Configuration {

	private final static String SERVER_SETTINGS = "src/main/resources/config/server.xml";
	private final static String CLIENT_SETTINGS = "src/main/resources/config/client.xml";
	private final static String GRPC_CLIENT_SETTINGS = "src/main/resources/config/grpcClient.xml";
	private final static String GRPC_SERVER_SETTINGS = "src/main/resources/config/grpcServer.xml";

	public static SocketSetting grpcClientSettings() {
		File file = new File( GRPC_CLIENT_SETTINGS );
		return FileReader.readXMLConfiguration( file );
	}
	
	public static SocketSetting grpcServerSettings(String id) {
		File file = new File( GRPC_SERVER_SETTINGS+"."+id );
		return FileReader.readXMLConfiguration( file );
	}

	/**
	 * Leitura do XML do servidor
	 * @return SocketSettings
	 */
	public static SocketSetting serverSettings(String id) {
		File file = new File( SERVER_SETTINGS+"."+id );
		return FileReader.readXMLConfiguration( file );
	}

	/**
	 * Leitura do XML do cliente
	 * @return SocketSettings
	 */
	public static SocketSetting clientSettings() {

		File file = new File( CLIENT_SETTINGS );
		return FileReader.readXMLConfiguration( file );

	}
	
}
