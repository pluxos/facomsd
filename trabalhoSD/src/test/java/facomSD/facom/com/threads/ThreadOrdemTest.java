//package facomSD.facom.com.threads;
//
//import static org.junit.Assert.assertTrue;
//
//import cliente.Client;
//
//public class ThreadOrdemTest implements Runnable {
//	
//	int chave;
//
//	public ThreadOrdemTest(int s) {
//		this.chave = s;
//	}
//
//	public void run() {
//		try {
//		Client c = new Client();
//		c.init();
//		//ESCREVE UM VALOR EM UM ITEM - 0 ATE 999
//	      for (int i = chave; i < (chave+1000); i++) {
//	        c.getObjectOutputStream().writeObject("create " + i + ":" + (i + 1) + "");
//	        String resposta = (String) c.getObjectInputStream().readObject();
//	        System.out.println(resposta);
//	        assertTrue(resposta.equals("Dados criados com sucesso"));
//	      }
//	      //LER O VALOR DE V
//	      c.getObjectOutputStream().writeObject("read "+((chave+1000)-1));
//	      String resposta = (String) c.getObjectInputStream().readObject();
//	      System.out.println(resposta);
//	      assertTrue(resposta.equals(Integer.toString((chave+1000))));
//	      int v = Integer.parseInt(resposta);
//	      //ESCREVER O VALOR DE A1000
//	      c.getObjectOutputStream().writeObject("create "+(chave+1000)+":" + (v + 1));
//	      resposta = (String) c.getObjectInputStream().readObject();
//	      System.out.println(resposta);
//	      assertTrue(resposta.equals("Dados criados com sucesso"));
//	      //CONFIRMAR QUE O VALOR DE A1000 E IGUAL A 1001
//	      c.getObjectOutputStream().writeObject("read "+(chave+1000) );
//	      resposta = (String) c.getObjectInputStream().readObject();
//	      System.out.println(resposta);
//	      assertTrue(resposta.equals(""+(v + 1)));
//	      
//		}
//		catch(Exception e) {
//			
//		}
//		
//	}
//
//}
