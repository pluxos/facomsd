package sistemas_distribuidos.Testes;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import sistemas_distribuidos.Server.Servidor;
import sistemas_distribuidos.Cliente.Cliente;


public class Teste {
    
 public static void main(String[] args) throws IOException, InterruptedException{
        Servidor servidor;
        String host = "";
        int porta = 0;
        File arquivo = new File("Porta_e_host.txt");
        if (arquivo.exists()) {
            FileReader arq = new FileReader("Porta_e_host.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";
            while ((linha = lerArq.readLine()) != null) {
                String[] porta_e_host = linha.split(" ");
                String cliente_ou_servidor = porta_e_host[0];
                 if (cliente_ou_servidor.toUpperCase().equals("CLIENTE")) {
                    // new Cliente("127.0.0.1", 1234).executa();
                    porta = Integer.parseInt(porta_e_host[1]);
                    host = porta_e_host[2];
                }
            }
            

            ArrayList<String> comandos = new ArrayList<String>(); //Comandos para o clientes
         
            
            comandos.add("INSERT 1 NATAN");
            comandos.add("UPDATE 1 TESTE");
            comandos.add("INSERT 3 JOAO");
            comandos.add("DELETE 1");
            comandos.add("UPDATE 1 MARCELO");
            
            for(int i =0;i<5;i++){
                Cliente c = new Cliente(host, porta,true,comandos.get(i));
                c.executa();
                
            }
            

            
            
            

        }
        //new Servidor(1234).executa();
       
    }
    
    
}
