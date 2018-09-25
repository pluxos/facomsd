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
    ServerApp s;
    s = new ServerApp();
    try {
      c.init();
      for(int i=1; i<=1000; i++) {
    	  c.getObjectOutputStream().writeObject("create "+i+":teste"+i+"");
          String resposta = (String) c.getObjectInputStream().readObject();
          System.out.println(resposta);
          assertTrue(resposta.equals("Dados criados com sucesso"));  
      }
      
      c.getObjectOutputStream().writeObject("read 999");
      String resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("teste"));
      c.getObjectOutputStream().writeObject("update 1000:teste");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("Dados criados com sucesso"));
      assertTrue(resposta.equals("teste"));
      c.getObjectOutputStream().writeObject("read 1000");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("teste"));

    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }    
    
  }
}
