import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.net.*;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.File;

public class Servidor implements Runnable{
	public int port;
    BlockingQueue<Comando> f1;
    Comando c;

    Servidor(BlockingQueue<Comando> f1){
        this.f1 = f1;
    }
	
	public void run() {
        try {
            
            Scanner scanner = new Scanner(new File("port1.txt"));
			while (scanner.hasNextInt()) {
                port = scanner.nextInt();
			}
            ServerSocket servidor = new ServerSocket(port);
            scanner.close();
            Socket menu;
            
            while(true){

                menu = servidor.accept();
                ObjectInputStream ois = new ObjectInputStream(menu.getInputStream());

                c = (Comando) ois.readObject();

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
                    
                    // servidor.close();
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