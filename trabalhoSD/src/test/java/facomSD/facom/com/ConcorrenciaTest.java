//package facomSD.facom.com;
//
//import java.io.File;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import facomSD.facom.com.threads.ThreadCrudNok;
//import facomSD.facom.com.threads.ThreadCrudOk;
//import facomSD.facom.com.threads.ThreadOrdemTest;
//import facomSD.facom.com.threads.ThreadStartServer;
//
//public class ConcorrenciaTest {
//  @Before
//  public void setUp() throws Exception {
//  }
//  
//  @Test
//  public void testRun() {
//    try {
//      File arquivo = new File("operacoes.log");
//      arquivo.delete(); // deleta arquivo de log
//      Thread[] t = new Thread[10];
//      Thread[] tNok = new Thread[10];
//      Thread[] tOrdem = new Thread[12];
//      Thread ts = new Thread(new ThreadStartServer());
//      ts.start();
//      Thread.sleep(3000);
//      for (int i = 0; i < 10; i++) {
//        t[i] = new Thread(new ThreadCrudOk(i));
//        t[i].start();
//      }
//      for (int i = 0; i < 10; i++) {
//        tNok[i] = new Thread(new ThreadCrudNok((i + 10) * 4));
//        tNok[i].start();
//      }
//      for (int i = 1; i <= 10; i++) {
//        tOrdem[i] = new Thread(new ThreadOrdemTest(i * 2000));
//        tOrdem[i].start();
//      }
//      for (int i = 0; i < 10; i++) {
//        t[i].join();
//        tNok[i].join();
//        tOrdem[i + 1].join();
//      }
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//  }
//}
