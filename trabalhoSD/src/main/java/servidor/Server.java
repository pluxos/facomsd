package servidor;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String args[]) {

		try {
			int numero,temp;
			ServerSocket serverSocket = new ServerSocket(9876);
			Socket socket = serverSocket.accept();
			Scanner s = new Scanner(socket.getInputStream());
			numero = s.nextInt();
			
			temp = numero *2;
			PrintStream print = new PrintStream(socket.getOutputStream());
			print.println(temp);
			
		} catch (Exception e) {
		}
	}

}
