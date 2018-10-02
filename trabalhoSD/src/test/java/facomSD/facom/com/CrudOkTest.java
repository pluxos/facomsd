package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cliente.Client;
import facomSD.facom.com.threads.ThreadStartServer;

public class CrudOkTest {
  @Before
  public void setUp() throws Exception {
  }
  
  @Test
  public void testRun() {
    try {
      Client c;
      c = new Client();
      File arquivo = new File("operacoes.log");
      arquivo.delete(); // deleta arquivo de log
      Thread ts = new Thread(new ThreadStartServer());
      ts.start();
      Thread.sleep(3000);
      c.init();
      // CREATE DO NOVO ITEM I
      c.getObjectOutputStream().writeObject("create 0:teste0");
      String resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("Dados criados com sucesso"));
      // READ DO ITEM I
      c.getObjectOutputStream().writeObject("read 0");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("teste0"));
      // UPDATE DO ITEM I
      c.getObjectOutputStream().writeObject("update 0:teste");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("Dados alterados com sucesso"));
      // READ DO ITEM I
      c.getObjectOutputStream().writeObject("read 0");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("teste"));
      // DELETE DO ITEM I
      c.getObjectOutputStream().writeObject("delete 0");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("Dados removidos com sucesso"));
      // READ DO ITEM I
      c.getObjectOutputStream().writeObject("read 0");
      resposta = (String) c.getObjectInputStream().readObject();
      System.out.println(resposta);
      assertTrue(resposta.equals("Dados nao encontrados"));
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
