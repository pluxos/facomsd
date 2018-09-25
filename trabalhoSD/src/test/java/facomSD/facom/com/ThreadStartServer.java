package facomSD.facom.com;

import servidor.ServerApp;

public class ThreadStartServer implements Runnable {

	public void run() {
		ServerApp.iniciar();
	}

}
