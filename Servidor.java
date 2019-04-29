import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.net.*;
import java.io.DataInputStream;
import java.io.IOException;

public class Servidor implements Runnable{

    BlockingQueue<Comando> f1;
    Comando c;

    Servidor(BlockingQueue<Comando> f1){
        this.f1 = f1;
    }
	
	public void run() {
        try {
            ServerSocket servidor = new ServerSocket(1234);
            Socket menu;
            
            int cmd, chave;
            String valor;
    
            menu = servidor.accept();
            System.out.println("conexão feita com: " + menu);

            DataInputStream dis = new DataInputStream(menu.getInputStream());

            while(true){
    
                cmd = dis.readInt();

                switch (cmd) {
                    case 1: //create
                        chave = dis.readInt();
                        valor = dis.readUTF();
                        c = new Comando(cmd, chave, valor);
                        f1.add(c); // adiciona comando à f1
                        break;

                    case 2: //read
                        chave = dis.readInt();
                        c = new Comando(cmd, chave, "");
                        f1.add(c); // adiciona comando à f1
                        break;

                    case 3: //update
                        chave = dis.readInt();
                        valor = dis.readUTF();
                        c = new Comando(cmd, chave, valor);
                        f1.add(c); // adiciona comando à f1
                        break;

                    case 4: //delete
                        chave = dis.readInt();
                        c = new Comando(cmd, chave, "");
                        f1.add(c); // adiciona comando à f1
                        break;
                    } 
                    
                    servidor.close();
                }    
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		
	
	
	public static void main (String args[]) {

        BlockingQueue<Comando> f1 = new LinkedBlockingDeque<>();
        BlockingQueue<Comando> f2 = new LinkedBlockingDeque<>();
        BlockingQueue<Comando> f3 = new LinkedBlockingDeque<>();

        Thread t1 = new Thread(new Servidor(f1), "commandListener");
        t1.start();
        Thread t2 = new Thread(new Servidor2(f1, f2, f3), "commandCopier");
        t2.start();
        Thread t3 = new Thread(new Servidor3(f2), "commandLogger");
        t3.start();
        Thread t4 = new Thread(new Servidor4(f3), "commandExecutor");
		t4.start();

	}
}