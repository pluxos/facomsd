package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sun.security.ntlm.Server;

import cliente.Client;
import servidor.ServerApp;

public class CrudOkTest {
  
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
      c.getObjectOutputStream().writeObject("create 5:teste");
      String resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("Dados criados com sucesso"));
      c.getObjectOutputStream().writeObject("read 5");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("teste"));
      c.getObjectOutputStream().writeObject("update 5:teste5");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("Dados alterados com sucesso"));
      c.getObjectOutputStream().writeObject("read 5");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("teste5"));
      c.getObjectOutputStream().writeObject("delete 5");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("Dados removidos com sucesso"));
      c.getObjectOutputStream().writeObject("read 5");
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
    
    //assertEqua
    
  }
}
