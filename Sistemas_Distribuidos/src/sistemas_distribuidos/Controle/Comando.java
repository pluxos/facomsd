package sistemas_distribuidos.Controle;

import java.util.ArrayList;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.PrintStream;
import java.io.OutputStream;

public class Comando {
    private Socket cliente;
    private String comando;
    
    public Comando(Socket cliente,String comando){
        this.cliente = cliente;
        this.comando = comando;
        
    }
    public synchronized Socket getCliente(){
        return this.cliente;
    }
    
    public synchronized String getComando(){
        return this.comando;
    }
    
    
    public void imprimir(){
        System.out.println("COMANDO: "+this.comando);
    }
}
