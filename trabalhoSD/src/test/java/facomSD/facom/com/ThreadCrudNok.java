package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import cliente.Client;

public class ThreadCrudNok implements Runnable {

	int chave;

	public ThreadCrudNok(int s) {
		this.chave = s;
	}

	public void run() {

		try {
			Client c = new Client();
			c.init();
			// CREATE DO ITEM I
			c.getObjectOutputStream().writeObject("create "+chave+":i");
			String resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			// CREATE DO ITEM I - JA CRIADO
			c.getObjectOutputStream().writeObject("create "+chave+":i");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados ja cadastrados"));
			// READ DO ITEM J - NAO EXISTENTE
			c.getObjectOutputStream().writeObject("read 9999999999");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados nao encontrados"));
			// UPDATE DO ITEM J - NAO EXISTENTE
			c.getObjectOutputStream().writeObject("update 9999999999:j");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados nao encontrados"));
			// CREATE DO ITEM J
			c.getObjectOutputStream().writeObject("create "+(chave+1)+":j");
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			// READ DO ITEM J
			c.getObjectOutputStream().writeObject("read "+(chave+1));
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("j"));
			// DELETE DO ITEM I
			c.getObjectOutputStream().writeObject("delete "+(chave+1));
			resposta = (String) c.getObjectInputStream().readObject();
			System.out.println(resposta);
			assertTrue(resposta.equals("Dados removidos com sucesso"));

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
