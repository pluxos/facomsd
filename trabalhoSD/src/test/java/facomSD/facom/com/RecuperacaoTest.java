package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import com.sun.security.ntlm.Server;

import cliente.Client;
import servidor.ServerApp;

public class RecuperacaoTest {
  
  Server server;
  @Before
  public void setUp() throws Exception {
  }
  
  @Test
  public void testRun() {
    Client c;
    
    ServerApp s;
    s = new ServerApp();
    try {
    
      Thread ts = new Thread(new ThreadStartServer());
      ts.start();
      Thread.sleep(5000);
      System.out.println("teste");
      c = new Client();
      c.init();
      ObjectOutputStream out = c.getObjectOutputStream();
      ObjectInputStream in = c.getObjectInputStream();
//      for(int i=0; i<5; i++) {
//    	  System.out.println("Executando"+" create "+i+":teste"+i+"");
//    	  out.writeObject("create "+i+":teste"+i+"");
//          String resposta = (String) in.readObject();
//          System.out.println(resposta);
//          assertTrue(resposta.equals("Dados criados com sucesso")); 
//          System.out.println("sucesso");
//      }
      Thread.sleep(5000);
//      ServerApp.stop();
//      ServerApp.iniciar();
//      
//      
     for(int i=0; i<5; i++) {
    	  c.getObjectOutputStream().writeObject("read "+i+"");
          String resposta = (String) c.getObjectInputStream().readObject();
          System.out.println(resposta);
          assertTrue(resposta.equals("teste"+i+""));  
      }
      
//      REPETINDO O PROCESSO COM NOVOS ITENS
//      for(int i=5; i<8; i++) {
//    	  c.getObjectOutputStream().writeObject("create "+i+":teste"+i+"");
//          String resposta = (String) c.getObjectInputStream().readObject();
//          System.out.println(resposta);
//          assertTrue(resposta.equals("Dados criados com sucesso"));  
//      }
////      MATAR SERVIDOR
////      REINICIAR SERVIDOR
//      for(int i=5; i<8; i++) {
//    	  c.getObjectOutputStream().writeObject("read "+i+"");
//          String resposta = (String) c.getObjectInputStream().readObject();
//          System.out.println(resposta);
//          assertTrue(resposta.equals("teste"+i+""));  
//      }
      
      
     
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
