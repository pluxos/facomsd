import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.net.*;
import java.io.ObjectInputStream;
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
    
            menu = servidor.accept();
            System.out.println("conexão feita com: " + menu);

            ObjectInputStream ois = new ObjectInputStream(menu.getInputStream());

            while(true){
    
                c = (Comando) ois.readObject();
                System.out.println(c.cmd + " " + c.chave + " " + c.valor);

                switch (c.cmd) {
                    case 1: //create
                        f1.add(c); // adiciona comando à f1
                        break;

                    case 2: //read
                        f1.add(c); // adiciona comando à f1
                        break;

                    case 3: //update
                        f1.add(c); // adiciona comando à f1
                        break;

                    case 4: //delete
                        f1.add(c); // adiciona comando à f1
                        break;
                    } 
                    
                    servidor.close();
                }    
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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