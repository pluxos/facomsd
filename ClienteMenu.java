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
			Socket cliente = new Socket(ip, 1234);

      DataInputStream dis = new DataInputStream(cliente.getInputStream()); 
      DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
			
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

									String resCreate = dis.readUTF(); // resultado da operação
									Sysem.out.println(resCreate); 
									break;
						case "R":	
									System.out.println("Read");
									Scanner read = new Scanner(System.in);

									System.out.println("Chave: ");
									byte[] chaveRead = read.nextBigInteger().toByteArray();

									dos.writeInt(2); // 2 = "read"
									dos.write(chaveRead);

									String resRead = dis.readUTF(); // resultado da operação
									Sysem.out.println(resRead);
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

									String resUpdate = dis.readUTF(); // resultado da operação
									System.out.println(resUpdate);
									break;
						case "D":
									System.out.println("Delete");
									Scanner delete = new Scanner(System.in);

									System.out.println("Chave: ");
									byte[] chaveDeleite = delete.nextBigInteger().toByteArray();

									dos.writeInt(4); // 4 = "delete"
									dos.write(chaveDeleite);

									String resDelete = dis.readUTF(); // resultado da operação
									System.out.println(resDelete);
									break;
						case "X": scanIn.close(); return;
						default: System.out.println("opcao inválida"); break;
						
					}
				}catch (InputMismatchException e){
					System.out.println("Formato de chave inválido");
					System.out.println("Operação não realizada");
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}
  }
	
	public static void main (String args[]) {
			Thread t1 = new Thread(new ClienteMenu(), "thread1");
			t1.start(); //inicia thread do menu
	}
}