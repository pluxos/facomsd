package server;


import io.grpc.stub.StreamObserver;
import java.io.IOException;
import threads.Consumidor;
import threads.EntryPoint;
import threads.Logger;
import threads.Persistence;
import java.net.ServerSocket;
import java.net.Socket;

class Server {

	public static void main(String[] args) {
		try {
//       Properties properties = new Properties();
//       FileInputStream propsFS = new FileInputStream("Server/src/main/resources/Constants.prop");
//       properties.load(propsFS);
//       Integer port = Integer.parseInt(properties.getProperty("port"));
			int port = 12345;
			ServerSocket server = new ServerSocket(port);
			System.out.println("Listening on port " + port);

			new Thread(new Persistence()).start();
			new Thread(new Logger()).start();
			new Thread(new Consumidor()).start();

			while (true) {
				Socket client = server.accept();
				new Thread(new EntryPoint(client)).start();
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}