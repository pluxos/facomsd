package com.SDgroup;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EntryPoint implements Runnable{
    private Socket cliente;
    
    EntryPoint(){
        this.cliente = new Socket();
    }
    
    public void run() {
        try {
            /*
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            saida.flush();
            saida.writeObject("teste1");
            saida.writeObject("teste2");
            saida.writeObject("teste3");
            
            saida.close();
            cliente.close();
            */
            ItemFila c = new ItemFila();
            
            F1 f1 =  F1.getInstance();
            synchronized(f1){
                System.out.println("Entrypoint no syncronized");
                while(true){
                    f1.queue(c);
                    System.out.println("emfileirado");
                    c.k ++;
                    f1.notify();
                    System.out.println("notificado");
                }
            }
        }   
        catch(Exception e) {
            e.printStackTrace();
        }
        // finally {...}
    }
}