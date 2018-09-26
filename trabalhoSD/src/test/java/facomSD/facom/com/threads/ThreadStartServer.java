package facomSD.facom.com.threads;

import servidor.Server;

public class ThreadStartServer implements Runnable {
	Server s;

	public ThreadStartServer(){
		s = new Server();
	}
	
	public Server getS() {
		return s;
	}

	public void run() {
		s.iniciar();
	}
}
