package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import com.sun.security.ntlm.Server;

import cliente.Client;
import servidor.ServerApp;

public class RecuperacaoTest {

	Server server;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() {
		Client c;

		try {
			Thread ts = new Thread(new ThreadStartServer());
			ts.start();
			Thread.sleep(5000);
			System.out.println("teste");
			c = new Client();
			c.init();
			ObjectOutputStream out = c.getObjectOutputStream();
			ObjectInputStream in = c.getObjectInputStream();
			//5 ITENS SAO CRIADOS
			for (int i = 0; i < 5; i++) {
				System.out.println("Executando" + " create " + i + ":teste" + i + "");
				out.writeObject("create " + i + ":teste" + i + "");
				String resposta = (String) in.readObject();
				System.out.println(resposta);
				assertTrue(resposta.equals("Dados criados com sucesso"));
				System.out.println("sucesso");
			}
			Thread.sleep(5000);
			//PROCESSO SERVIDOR E MORTO
			ts.interrupt();
			Thread.sleep(5000);
			ts = new Thread(new ThreadStartServer());
			//PROCESSO SERVIDOR E INICIADO
			ts.start();
			Thread.sleep(5000);
			//5 ITENS CRIADOS ANTERIORMENTE SAO LIDOS
			for (int i = 0; i < 5; i++) {
				c.getObjectOutputStream().writeObject("read " + i + "");
				String resposta = (String) c.getObjectInputStream().readObject();
				System.out.println(resposta);
				assertTrue(resposta.equals("teste" + i + ""));
			}
			//REPETICAO DO TESTE COM 3 NOVOS ITENS
			for (int i = 5; i < 8; i++) {
				c.getObjectOutputStream().writeObject("create " + i + ":teste" + i + "");
				String resposta = (String) c.getObjectInputStream().readObject();
				System.out.println(resposta);
				assertTrue(resposta.equals("Dados criados com sucesso"));
			}
			Thread.sleep(5000);
			ts.interrupt();
			Thread.sleep(5000);
			ts = new Thread(new ThreadStartServer());
			ts.start();
			Thread.sleep(5000);
			for (int i = 5; i < 8; i++) {
				c.getObjectOutputStream().writeObject("read " + i + "");
				String resposta = (String) c.getObjectInputStream().readObject();
				System.out.println(resposta);
				assertTrue(resposta.equals("teste" + i + ""));
			}

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
