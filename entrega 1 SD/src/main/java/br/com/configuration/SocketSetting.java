package br.com.configuration;

public class SocketSetting {

	private String host;
	
	private int port;
	
	public SocketSetting() {}
	
	public SocketSetting( String host, int port ) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
