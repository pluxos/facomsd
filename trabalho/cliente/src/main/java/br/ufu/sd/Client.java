package br.ufu.sd;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class Client {
	private static Socket socket = null;
	private static Config config = new Config();
	
	public void Initialize(InputStream _input) {
		String host;
		int port;

		config.load();

		if (config.getIsLoaded()) {
			port = config.getPort();
			host = config.getServer();
			try {
				socket = new Socket(host, port);

				Thread menu = null;

				menu = new Thread(new MenuThread(new ObjectOutputStream(socket.getOutputStream()), _input));
				menu.setDaemon(true);

				Thread response = new Thread(new ResponseThread(socket));
				response.setDaemon(true);

				menu.start();
				response.start();

				menu.join();
				response.join(5000, 0);

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Não foi possivel se conectar ao servidor: " + host + ':' + port);
				//e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Client cliente = new Client();
		cliente.Initialize(System.in);
	}
}
