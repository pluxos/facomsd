package com.SDgroup;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EntryPoint implements Runnable{
    private Integer port;
    EntryPoint(Integer port){
        this.port = port;
    }

    public void run() {
        try {
          // Instancia o ServerSocket ouvindo a porta 12345
          ServerSocket servidor = new ServerSocket(port);
          System.out.println("Servidor ouvindo a porta 12345");
          while(true) {
            // o método accept() bloqueia a execução até que
            // o servidor receba um pedido de conexão
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            saida.flush();
            saida.writeObject("teste1");
            saida.writeObject("teste2");
            saida.writeObject("teste3");
            saida.close();
            cliente.close();
          }  
        }   
        catch(Exception e) {
          System.out.println("Erro: " + e.getMessage());
        }
        // finally {...}
      }
}