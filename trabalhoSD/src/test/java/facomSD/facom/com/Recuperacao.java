package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import facomSD.facom.com.threads.ThreadStartRing;
import facomSD.facom.com.threads.ThreadStartServerExistente;
import utils.Constant;

public class Recuperacao {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() {
		try {
			ClientTests c = new ClientTests();
			File arquivo = new File("respostas.log");//nao comentar
			try {
				arquivo.delete(); // deleta arquivo de respostas
				arquivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			/* Bloco 1 */

//         /*
			Thread t = new Thread(new ThreadStartRing(4, 4));
			t.start();
			Thread.sleep(4000);
			c.activate(); 
			//CREATE EM CADA SERVER
			c.greet("create 5:teste5");
			String resposta = getResposta(arquivo, 1);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			c.greet("create 10:teste10");
			resposta = getResposta(arquivo, 2);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			c.greet("create 15:teste15");
			resposta = getResposta(arquivo, 3);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			t.stop();
			if(!t.isAlive()) {
				System.out.println(" 	---------------------- Interrompida -------------------------");
			}
			// t.destroy();
			// Thread.sleep(4000);
//		*/		
			
			
			
			
			
			
			
			
			/* Bloco 2 */
			
//			/*			
//			Constant.setMaxKey("15"); //se comentar a primeira parte é preciso setar maxKey
			Thread t0 = new Thread(new ThreadStartServerExistente(BigInteger.valueOf(0)));
			Thread t1 = new Thread(new ThreadStartServerExistente(BigInteger.valueOf(1)));
			Thread t2 = new Thread(new ThreadStartServerExistente(BigInteger.valueOf(2)));
			Thread t3 = new Thread(new ThreadStartServerExistente(BigInteger.valueOf(3)));
			t0.start();
			t1.start();
			t2.start();
			t3.start();
			String resposta2;
			Thread.sleep(4000);
			ClientTests c2 = new ClientTests();
			c2.activate();
			System.out.println("iniciando testes");
			// READ EM CADA SERVER
			c2.greet("read 5");
			resposta2 = getResposta(arquivo, 4);//4 se nao comentar aprimeira parte
			assertTrue(resposta2.equals("teste5"));
			c2.greet("read 10");
			resposta2 = getResposta(arquivo, 5);//5 se nao comentar aprimeira parte
			assertTrue(resposta2.equals("teste10"));
			c2.greet("read 15");
			resposta2 = getResposta(arquivo, 6);//6 se nao comentar aprimeira parte
			assertTrue(resposta2.equals("teste15"));
//			*/		
			

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getResposta(File f, int n) {
		String linha = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while (n > 0) {
				while ((linha = br.readLine()) == null) {
				}
				n = n - 1;
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return linha;
	}

}
