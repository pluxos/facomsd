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
//public class OrdemTest {
//  @Before
//  public void setUp() throws Exception {
//  }
//  
//  @Test
//  public void testRun() {
//    Client c;
//    c = new Client();
//    try {
//      File arquivo = new File("operacoes.log");
//      arquivo.delete(); // deleta arquivo de log
//      Thread ts = new Thread(new ThreadStartServer());
//      ts.start();
//      Thread.sleep(3000);
//      c.init();
//      //ESCREVE UM VALOR EM UM ITEM - 0 ATE 999
//      for (int i = 0; i < 1000; i++) {
//        c.getObjectOutputStream().writeObject("create " + i + ":" + (i + 1) + "");
//        String resposta = (String) c.getObjectInputStream().readObject();
//        System.out.println(resposta);
//        assertTrue(resposta.equals("Dados criados com sucesso"));
//      }
//      //LER O VALOR DE V
//      c.getObjectOutputStream().writeObject("read 999");
//      String resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("1000"));
//      int v = Integer.parseInt(resposta);
//      //ESCREVER O VALOR DE A1000
//      c.getObjectOutputStream().writeObject("create 1000:" + (v + 1));
//      resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("Dados criados com sucesso"));
//      //CONFIRMAR QUE O VALOR DE A1000 E IGUAL A 1001
//      c.getObjectOutputStream().writeObject("read 1000");
//      resposta = (String) c.getObjectInputStream().readObject();
//      System.out.println(resposta);
//      assertTrue(resposta.equals("1001"));
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
