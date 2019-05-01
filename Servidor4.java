import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.math.BigInteger;
import java.net.*;

public class Servidor4 implements Runnable{

    BlockingQueue<Comando> f3;
    Comando c;

    Servidor4(BlockingQueue<Comando> f3){
        this.f3 = f3;
    }
	
	public void run() {
            

        String valor;
        byte[] valorByte;
        Map<BigInteger, byte[]> mapa = new Hashtable<BigInteger, byte[]>();

        while(true){

            if(!f3.isEmpty()){
                c = f3.poll();
                // operacão com banco(mapa)
                switch (c.cmd) {
                    case 1: //create

                        valorByte = c.valor.getBytes();
                        mapa.put(c.chave, valorByte);

                        break;

                    case 2: //read

                        valorByte = mapa.get(c.chave);
                        valor = Base64.getEncoder().encodeToString(valorByte);

                        break;

                    case 3: //update

                        valorByte = c.valor.getBytes();
                        mapa.replace(c.chave, valorByte);

                        break;

                    case 4: //delete
                        
                        mapa.remove(c.chave);

                        break;
                    } 
            }
        }    
    }
}