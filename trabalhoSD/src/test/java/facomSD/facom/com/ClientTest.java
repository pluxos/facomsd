package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sun.security.ntlm.Server;

import cliente.Client;

public class ClientTest {
  
  Server server;
  @Before
  public void setUp() throws Exception {
  }
  
  @Test
  public void testRun() {
    Client c;
    c = new Client();
    try {
      c.init();
      c.getObjectOutputStream().writeObject("create 2:teste");
      String resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("Dados Criados com sucesso"));
      c.getObjectOutputStream().writeObject("read 2");
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
    
    //assertEqua
    
  }
}
