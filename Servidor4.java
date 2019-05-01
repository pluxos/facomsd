import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.math.BigInteger;
import java.net.*;
import java.io.*;

public class Servidor4 implements Runnable{

    BlockingQueue<Comando> f3;
    public int port;
    
    Servidor4(BlockingQueue<Comando> f3){
        this.f3 = f3;
    }
	
	public void run() {
        
        try {
            
            Comando c;
            Scanner scanner = new Scanner(new File("port2.txt"));
			while (scanner.hasNextInt()) {
                port = scanner.nextInt();
            }
            
            ServerSocket servidor = new ServerSocket(port);
            scanner.close();
            Socket listener;
            
            String valor;
            Map<BigInteger, byte[]> mapa = new Hashtable<BigInteger, byte[]>();
            listener = servidor.accept();
            DataOutputStream dos = new DataOutputStream(listener.getOutputStream());
            
            while(true){
                
                
                if(!f3.isEmpty()){
                    c = f3.poll();
                    // operacão com banco(mapa)
                    switch (c.cmd) {
                        case 1: //create
                        
                        if(!mapa.containsKey(c.chave)){
                            byte[] valorByte = c.valor.getBytes();
                            mapa.put(c.chave, valorByte);
                            dos.writeUTF("Inserção realizada com sucesso");
                        }else{
                            dos.writeUTF("Erro: chave já existente");
                        }
                        
                        break;
                        
                        case 2: //read
                        
                        if(mapa.containsKey(c.chave)){
                            byte[] valorByte = mapa.get(c.chave);
                            valor = new String(valorByte);
                            dos.writeUTF("valor: " + valor);
                        }else{
                            dos.writeUTF("Erro: a chave não existe");
                        }
                        
                        break;
                        
                        case 3: //update
                        
                        if(mapa.containsKey(c.chave)){
                            byte[] valorByte = c.valor.getBytes();
                            mapa.replace(c.chave, valorByte);
                            dos.writeUTF("valor alterado com sucesso");
                        }else{
                            dos.writeUTF("Erro: a chave não existe");
                        }
                        
                        break;
                        
                        case 4: //delete
                        
                        if(mapa.containsKey(c.chave)){
                            mapa.remove(c.chave);
                            dos.writeUTF("Remoção realizada com sucesso");
                        }else{
                            dos.writeUTF("chave não existente");
                        }
                        
                        break;
                    } 
                }
                servidor.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}