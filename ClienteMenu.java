import java.util.*;
public class ClienteMenu implements Runnable{
	
	public void run() {
      
			String input="";
			Scanner scanIn = new Scanner(System.in);
			
			while (true){
			
				System.out.println("C - Create");
				System.out.println("R - Read");
				System.out.println("U - Update");
				System.out.println("D - Delete");
				System.out.println("X - eXit");
				System.out.println();
				
				
				input = scanIn.nextLine();  
			
			
				switch (input.toUpperCase()){
					case "C":
								input = "";
								System.out.println("Digite a string ser criada:");
								input = scanIn.nextLine();
								System.out.println(input);
								if(input != "") {
									System.out.println("Validado");
									try {
										socket = new Socket("ip address", 4014);
										osw =new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
										osw.write(input, 0, input.length());
									} catch (IOException e) {
										System.err.print(e);
									} catch (UnknownHostException e) {
										System.err.print(e);
									} finally {
										socket.close();
									}
								}
								else
									System.out.println("Invalidado");
								break;
					case "R":	
								System.out.println("Read");
								break;
					case "U":
								System.out.println("Update");
								break;
					case "D":
								System.out.println("Delete");
								break;
					case "X": return;
					default: System.out.println("opcao invalida"); break;
					
				}
			}
    }
		
	
	
	public static void main (String args[]) {
			Thread t1 = new Thread(new ClienteMenu(), "thread1");
			t1.start();
	}
	
	
	
	
	
}