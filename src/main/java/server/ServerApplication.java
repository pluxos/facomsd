package server;
import java.io.IOException;

import server.receptor.*;

public class ServerApplication {
	public static void main(String[] args) throws IOException {
		ReceptorMain rm = new ReceptorMain();
		rm.run();
	}
}
