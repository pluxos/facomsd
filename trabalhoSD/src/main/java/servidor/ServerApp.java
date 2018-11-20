package servidor;

import java.math.BigInteger;
import java.util.Scanner;

public class ServerApp {
	public static void main(String[] args) {
		try {
			Node server = new Node();
			Scanner s = new Scanner(System.in);
			System.out.println("1-novo server\n2-Server existente");
			int opcao = s.nextInt();
			BuildRing r= new BuildRing();
			if (opcao == 1) {
				
				System.out.println("Numero de nos: ");
				int n = s.nextInt();
				System.out.println("Tamanho m: ");
				int m = s.nextInt();
				r.buildRing(n, m);
				
//				System.out.println("porta id minKey maxKey antecessor sucessor: ");
//				int porta = s.nextInt();
//				int id = s.nextInt();
//				int minKey = s.nextInt();
//				int maxKey = s.nextInt();
//				int antecessor = s.nextInt();
//				int sucessor = s.nextInt();
//				server.start(Constant.HOST, porta, BigInteger.valueOf(id), BigInteger.valueOf(minKey),
//						BigInteger.valueOf(maxKey), antecessor, sucessor);
			} else if (opcao == 2) {
				System.out.println("Id do servidor a restaurar: ");
				server.start(BigInteger.valueOf((s.nextInt())));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
