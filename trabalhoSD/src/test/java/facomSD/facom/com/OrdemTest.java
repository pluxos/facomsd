package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sun.security.ntlm.Server;

import cliente.Client;
import servidor.ServerApp;

public class OrdemTest {

	Server server;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() {
		Client c;
		c = new Client();
		try {
			Thread ts = new Thread(new ThreadStartServer());
			ts.start();
			Thread.sleep(5000);
			c.init();
			for (int i = 0; i < 1000; i++) {
				c.getObjectOutputStream().writeObject("create " + i + ":" + (i + 1) + "");
				String resposta = (String) c.getObjectInputStream().readObject();
				System.out.println(resposta);
				assertTrue(resposta.equals("Dados criados com sucesso"));
			}
			Thread.sleep(5000);
			c.getObjectOutputStream().writeObject("read 999");
			String resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("1000"));
			int v = Integer.parseInt(resposta);
			c.getObjectOutputStream().writeObject("create 1000:"+(v+1));
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			c.getObjectOutputStream().writeObject("read 1000");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("1001"));

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
