import java.util.*;
import java.net.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class ClienteMenu implements Runnable {
	
	public void run() {
      
		try{
			String input="";
			Scanner scanIn = new Scanner(System.in);

			InetAddress ip = InetAddress.getByName("localhost");
			Socket menu = new Socket(ip, 1234);

      		DataOutputStream dos = new DataOutputStream(menu.getOutputStream());
			
			while (true){
			
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

									System.out.println("Create");
									Scanner create = new Scanner(System.in);

									System.out.println("Chave: ");
									byte[] chaveCreate = create.nextBigInteger().toByteArray();
									System.out.println("Valor: ");
									String valorCreate = create.next();

									if(valorCreate.length() > 100){
										System.out.println("Valor ultrapaça tamanho máximo permitido");
										System.out.println("Operação não realizada");
										break;
									}
									byte[] createByte = valorCreate.getBytes();

									dos.writeInt(1); // 1 = "create"
									dos.write(chaveCreate);
									dos.write(createByte);
									break;
						case "R":	
									System.out.println("Read");
									Scanner read = new Scanner(System.in);

									System.out.println("Chave: ");
									byte[] chaveRead = read.nextBigInteger().toByteArray();

									dos.writeInt(2); // 2 = "read"
									dos.write(chaveRead);
									break;
						case "U":
									System.out.println("Update");
									Scanner update = new Scanner(System.in);

									System.out.println("Chave: ");
									byte[] chaveUpdate= update.nextBigInteger().toByteArray();
									
									System.out.println("Novo valor: ");
									String valorUpdate = update.next();
									if(valorUpdate.length() > 100){
										System.out.println("Valor ultrapaça tamanho máximo permitido");
										System.out.println("Operação não realizada");
										break;
									}
									
									byte[] updateByte = valorUpdate.getBytes();
									dos.writeInt(3); // 3 = "update"
									dos.write(chaveUpdate);
									dos.write(updateByte);
									break;
						case "D":
									System.out.println("Delete");
									Scanner delete = new Scanner(System.in);

									System.out.println("Chave: ");
									byte[] chaveDeleite = delete.nextBigInteger().toByteArray();

									dos.writeInt(4); // 4 = "delete"
									dos.write(chaveDeleite);
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
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
  }
	
	public static void main (String args[]) {
			Thread t1 = new Thread(new ClienteMenu(), "threadMenu");
			t1.start(); //inicia thread do menu
			Thread t2 = new Thread(new ClienteListener(t1), "threadListener");
			t2.start();
	}
}