package com.SistemasDistribuidos.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author luizedp
 *
 */
public class ProcessaCliente extends Thread {
	DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
    final DataInputStream input; 
    final DataOutputStream output; 
    final Socket socket; 
      
  
    // Construtor 
    public ProcessaCliente(Socket socket, DataInputStream input, DataOutputStream output)  
    { 
        this.socket = socket; 
        this.input = input; 
        this.output = output; 
    } 
  
    @Override
    public void run()  
    { 
        String received; 
        String toreturn; 
        while (true)  
        { 
            try { 
  
                // Cria o menu de ações para o cliente 
                output.writeUTF(" Digite create para inserir dados. \n "
                		+ "Digite update para atualizar. \n "
                		+ "Digite read para ler os dados. \n "
                		+ "Digite delete para apagar os dados. \n "
                		+ "Digite exit para fechar a conexão.\n\n "); 
                  
                // recebe a ação do cliente (input) 
                received = input.readUTF(); 
                  
                if(received.equals("exit")) 
                {  
                    System.out.println("Cliente " + this.socket + " enviando comando para fechar conexão..."); 
                    System.out.println("Fechando conecção."); 
                    this.socket.close(); 
                    System.out.println("Conexão fechada"); 
                    break; 
                }                  
                 
                Date date = new Date(); 
                  
                // escrevendo uma stream de saída baseada na resposta do cliente 
                if(received.equals("create")) {
                	toreturn = fordate.format(date); 
                    output.writeUTF(toreturn); 
                }else if(received.equals("update")){
                	output.writeUTF("teste1");                	
                }else if(received.equals("read")) {
                	output.writeUTF("teste2");                	
                }else if(received.equals("delete")) {
                	output.writeUTF("teste3");                	
                }else {
                	output.writeUTF("Entrada inválida! Por favor, digite uma entrada válida.");                    
                } 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        try
        { 
            // fechando entrada e saída 
            this.input.close(); 
            this.output.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
}
