package facomSD.facom.com.threads;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import cliente.Client;

public class ThreadCrudOk implements Runnable {
	int chave;

	public ThreadCrudOk(int s) {
		this.chave = s;
	}

	public void run() {

		try {
			Client c = new Client();
			c.init();
			// CREATE DO NOVO ITEM I
			c.getObjectOutputStream().writeObject("create " + chave + ":teste" + chave);
			System.out.println("a");
			String resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			// READ DO ITEM I
			c.getObjectOutputStream().writeObject("read " + chave + "");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("teste" + chave));
			// UPDATE DO ITEM I
			c.getObjectOutputStream().writeObject("update " + chave + ":teste");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados alterados com sucesso"));
			// READ DO ITEM I
			c.getObjectOutputStream().writeObject("read " + chave + "");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("teste"));
			// DELETE DO ITEM I
			c.getObjectOutputStream().writeObject("delete " + chave + "");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados removidos com sucesso"));
			// READ DO ITEM I
			c.getObjectOutputStream().writeObject("read " + chave + "");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados nao encontrados"));
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
