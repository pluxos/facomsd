package facomSD.facom.com;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sun.security.ntlm.Server;

import cliente.Client;
import servidor.ServerApp;

public class CrudNokTest {

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
			c.getObjectOutputStream().writeObject("create 0:teste");
			String resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			c.getObjectOutputStream().writeObject("create 0:teste");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados ja cadastrados"));
			c.getObjectOutputStream().writeObject("read 1");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados nao encontrados"));
			c.getObjectOutputStream().writeObject("update 1:teste");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados nao encontrados"));
			c.getObjectOutputStream().writeObject("read 1");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados nao encontrados"));
			c.getObjectOutputStream().writeObject("delete 0");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados removidos com sucesso"));

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
