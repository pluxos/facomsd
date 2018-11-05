//package facomSD.facom.com;
//
//import static org.junit.Assert.assertTrue;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import cliente.Client;
//import facomSD.facom.com.threads.ThreadStartServer;
//
//public class RecuperacaoTest {
//  @Before
//  public void setUp() throws Exception {
//  }
//  
//  @Test
//  public void testRun() {
//    Client c;
//    try {
//      ThreadStartServer t = new ThreadStartServer();
//      Thread ts = new Thread(t);
//      ts.start();
//      Thread.sleep(3000);
//      System.out.println("teste");
//      c = new Client();
//      c.init();
//      ObjectOutputStream out = c.getObjectOutputStream();
//      ObjectInputStream in = c.getObjectInputStream();
//      // 5 ITENS SAO CRIADOS
//      for (int i = 0; i < 5; i++) {
//        System.out.println("Executando" + " create " + i + ":teste" + i + "");
//        out.writeObject("create " + i + ":teste" + i + "");
//        String resposta = (String) in.readObject();
//        System.out.println(resposta);
//        assertTrue(resposta.equals("Dados criados com sucesso"));
//        System.out.println("sucesso");
//      }
//      Thread.sleep(3000);
//      // PROCESSO SERVIDOR E MORTO(Nao funcionando!)
//      // t.getS().stop();
//      // Thread.sleep(5000);
//      // ts = new Thread(new ThreadStartServer());
//      // PROCESSO SERVIDOR E INICIADO
//      ts.start();
//      Thread.sleep(3000);
//      // 5 ITENS CRIADOS ANTERIORMENTE SAO LIDOS
//      for (int i = 0; i < 5; i++) {
//        c.getObjectOutputStream().writeObject("read " + i + "");
//        String resposta = (String) c.getObjectInputStream().readObject();
//        System.out.println(resposta);
//        assertTrue(resposta.equals("teste" + i + ""));
//      }
//      // REPETICAO DO TESTE COM 3 NOVOS ITENS
//      for (int i = 5; i < 8; i++) {
//        c.getObjectOutputStream().writeObject("create " + i + ":teste" + i + "");
//        String resposta = (String) c.getObjectInputStream().readObject();
//        System.out.println(resposta);
//        assertTrue(resposta.equals("Dados criados com sucesso"));
//      }
//      // PROCESSO SERVIDOR E MORTO(Nao funcionando!)
//      // t.getS().stop();
//      // Thread.sleep(5000);
//      // ts = new Thread(new ThreadStartServer());
//      // PROCESSO SERVIDOR E INICIADO
//      ts.start();
//      Thread.sleep(3000);
//      for (int i = 5; i < 8; i++) {
//        c.getObjectOutputStream().writeObject("read " + i + "");
//        String resposta = (String) c.getObjectInputStream().readObject();
//        System.out.println(resposta);
//        assertTrue(resposta.equals("teste" + i + ""));
//      }
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
