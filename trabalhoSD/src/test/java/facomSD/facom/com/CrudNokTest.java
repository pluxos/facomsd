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
	    ServerApp s;
	    s = new ServerApp();
	    try {
	      c.init();
	      c.getObjectOutputStream().writeObject("create 1:teste");
	      String resposta = (String) c.getObjectInputStream().readObject();
	      System.out.println(resposta);
	      assertTrue(resposta.equals("Dados criados com sucesso"));
	      c.getObjectOutputStream().writeObject("create 1:teste");
	      resposta = (String) c.getObjectInputStream().readObject();
	      System.out.println(resposta);
	      assertTrue(resposta.equals("Key ja cadastrada"));
	      c.getObjectOutputStream().writeObject("read 2");
	      resposta = (String) c.getObjectInputStream().readObject();
	      System.out.println(resposta);
	      assertTrue(resposta.equals("Dados nao encontrados"));
	      c.getObjectOutputStream().writeObject("update 2:teste");
	      resposta = (String) c.getObjectInputStream().readObject();
	      System.out.println(resposta);
	      assertTrue(resposta.equals("Key nao encontrada"));
	      c.getObjectOutputStream().writeObject("delete 1");
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
