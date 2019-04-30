import java.util.*;
import java.net.*;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class ClienteMenu implements Runnable {
	
	public void run() {
      
		try{
			String input="";
			BigInteger chave;
			String valor;
			Scanner scanIn = new Scanner(System.in);
			Comando c;

			InetAddress ip = InetAddress.getByName("localhost");
			
			
			while (true){
				Socket menu = new Socket(ip, 1234);
				ObjectOutputStream oos = new ObjectOutputStream(menu.getOutputStream());
			
				System.out.println("C - Create");
				System.out.println("R - Read");
				System.out.println("U - Update");
				System.out.println("D - Delete");
				System.out.println("X - eXit");
				System.out.println();
				
				
				input = scanIn.nextLine();
				
			
				try{
					switch (input.toUpperCase()){
						case "C":

									System.out.println("Create\n");
									Scanner create = new Scanner(System.in);

									System.out.print("Chave: ");
									chave = create.nextBigInteger();
									System.out.print("Valor: ");
									valor = create.next();

									if(valor.length() > 100){
										System.out.println("Valor ultrapaça tamanho máximo permitido");
										System.out.println("Operação não realizada");
										break;
									}
									
									c = new Comando(1, chave, valor);
									oos.writeObject(c);
									break;
						case "R":	
									System.out.println("Read\n");
									Scanner read = new Scanner(System.in);

									System.out.print("Chave: ");
									chave = read.nextBigInteger();

									c = new Comando(2, chave, "");
									oos.writeObject(c);
									break;
						case "U":
									System.out.println("Update\n");
									Scanner update = new Scanner(System.in);

									System.out.print("Chave: ");
									chave = update.nextBigInteger();
									
									System.out.print("Novo valor: ");
									valor = update.next();

									if(valor.length() > 100){
										System.out.println("Valor ultrapaça tamanho máximo permitido");
										System.out.println("Operação não realizada");
										break;
									}

									c = new Comando(3, chave, valor);
									oos.writeObject(c);
									break;
						case "D":
									System.out.println("Delete\n");
									Scanner delete = new Scanner(System.in);

									System.out.print("Chave: ");
									chave = delete.nextBigInteger();

									c = new Comando(4, chave, "");
									oos.writeObject(c);
									break;
						case "X": 
									scanIn.close(); 
									menu.close(); 
									return;
						default: 
									System.out.println("opcao inválida"); break;
						
					}
				}catch (InputMismatchException e){
					System.out.println("Formato de chave inválido");
					System.out.println("Operação não realizada");
				} // tratamento de chave inválida
				  // continua dentro do loop
			}
		}catch (IOException e) {
		}
  }
	
	public static void main (String args[]) {
			Thread t1 = new Thread(new ClienteMenu(), "threadMenu");
			t1.start(); // inicia thread do menu
			Thread t2 = new Thread(new ClienteListener(t1), "threadListener");
			t2.start(); // inicia thread do listener
	}
}