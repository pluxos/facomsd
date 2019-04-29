import java.util.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class Servidor implements Runnable{
	
	public void run() {
        try {
            ServerSocket servidor = new ServerSocket(1234);
            ServerSocket servidor1 = new ServerSocket(5678);
            Socket menu, listener;
            
            int cmd, chave;
            String msg;
    
            menu = servidor.accept();
            listener = servidor1.accept();
            System.out.println("conexão feita com: " + menu);
            System.out.println("conexão feita com: " + listener);

            DataInputStream dis = new DataInputStream(menu.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(listener.getOutputStream());

            while(true){
    
                cmd = dis.readInt();

                switch (cmd) {
                    case 1: //create
                        System.out.println("Create");
                        chave = dis.readInt();
                        msg = dis.readUTF();
                        dos.writeUTF("Created - chave: " + chave + "  valor: " + msg);
                        break;

                    case 2: //read
                        System.out.println("Read");
                        chave = dis.readInt();
                        dos.writeUTF("Readed - chave: " + chave);
                        break;

                    case 3: //update
                        System.out.println("Update");
                        chave = dis.readInt();
                        msg = dis.readUTF();
                        dos.writeUTF("Updated - chave: " + chave + "  valor: " + msg);
                        break;

                    case 4: //delete
                        System.out.println("Delete");
                        chave = dis.readInt();
                        dos.writeUTF("Deleted - chave: " + chave);
                        break;
                    } 
                    
                    servidor.close();
                    servidor1.close();
                }    
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		
	
	
	public static void main (String args[]) {
        Thread t = new Thread(new Servidor(), "servidor");
        t.start();
        // Thread t2 = new Thread(new Servidor2(), "thread2");
        // t2.start();
        // Thread t3 = new Thread(new Servidor3(), "thread3");
        // t3.start();
        // Thread t4 = new Thread(new Servidor4(), "thread4");
		// t4.start();

	}
	
	
	
	
	
}