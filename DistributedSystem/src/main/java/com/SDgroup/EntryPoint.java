package com.SDgroup;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EntryPoint implements Runnable{
    private Socket cliente;

    EntryPoint(Socket cliente){
       this.cliente = cliente;
    }
    
    public void run() {
        try {
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                saida.flush();
                saida.writeObject("teste1");
                saida.writeObject("teste2");
                saida.writeObject("teste3");
                
                saida.close();
                cliente.close();
        }   
        catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        // finally {...}
    }
}