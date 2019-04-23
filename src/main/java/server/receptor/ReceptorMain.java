package server.receptor;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ReceptorMain {
	private ServerSocket server;

	public void run() throws IOException {
		this.server = new ServerSocket(12345);
		System.out.println("Server TCP startado na porta 12345");
		
		Socket cliente = this.server.accept();
		System.out.println("Nova conexão com o cliente " + 
		    cliente.getInetAddress().getHostAddress()
		);
		
		Scanner s = new Scanner(cliente.getInputStream());
        while (s.hasNextLine()) {
            System.out.println(s.nextLine());
        }

        s.close();
        this.server.close();
        cliente.close();
	}
}
