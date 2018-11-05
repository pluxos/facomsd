//package facomSD.facom.com;
//
//import static org.junit.Assert.assertTrue;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import cliente.Client;
//import facomSD.facom.com.threads.ThreadStartServer;
//
//public class CrudNokTest {
//  @Before
//  public void setUp() throws Exception {
//  }
//  
//  @Test
//  public void testRun() {
//    try {
//      Client c;
//      c = new Client();
//      File arquivo = new File("operacoes.log");
//      arquivo.delete(); // deleta arquivo de log
//      Thread ts = new Thread(new ThreadStartServer());
//      ts.start();
//      Thread.sleep(3000);
//      c.init();
//      // CREATE DO ITEM I
//      c.getObjectOutputStream().writeObject("create 0:i");
//      String resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("Dados criados com sucesso"));
//      // CREATE DO ITEM I - JA CRIADO
//      c.getObjectOutputStream().writeObject("create 0:i");
//      resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("Dados ja cadastrados"));
//      // READ DO ITEM J - NAO EXISTENTE
//      c.getObjectOutputStream().writeObject("read 1");
//      resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("Dados nao encontrados"));
//      // UPDATE DO ITEM J - NAO EXISTENTE
//      c.getObjectOutputStream().writeObject("update 1:j");
//      resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("Dados nao encontrados"));
//      // CREATE DO ITEM J
//      c.getObjectOutputStream().writeObject("create 1:j");
//      resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("Dados criados com sucesso"));
//      // READ DO ITEM J
//      c.getObjectOutputStream().writeObject("read 1");
//      resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("j"));
//      // DELETE DO ITEM I
//      c.getObjectOutputStream().writeObject("delete 1");
//      resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("Dados removidos com sucesso"));
//    } catch (ClassNotFoundException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    } catch (InterruptedException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//  }
//}
