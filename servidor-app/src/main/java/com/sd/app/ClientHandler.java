package com.sd.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

class ClientHandler implements Runnable{

    protected BlockingQueue<ItemFila> queue;
    private Socket socketClient;
    boolean erro;
    String tipo;
    BigInteger chave;
    String valor;
    String operacao;
    int escolha = 0;
    
    
    ClientHandler(Socket socketClient) {
        this.socketClient = socketClient;
        this.queue = F1.getInstance();
    }
    
    public void run() {
       
        try {
            System.out.println("Cliente conectado: " + socketClient.getInetAddress().getHostAddress());
            
            DataInputStream input = new DataInputStream(socketClient.getInputStream());
            DataOutputStream out = new DataOutputStream(socketClient.getOutputStream());
            
            while(true) {
                try {
                    
                 
                    
                    tipo = input.readUTF();
                    
                    tipo = tipo.substring( 0, tipo.indexOf(" ") );
                    operacao = tipo;
                    
                    if( tipo.equals("SAIR") ) break;
               
                    
                    else if( tipo.equals("CREATE") || tipo.equals("UPDATE")) {
                    	escolha = 1;
                        chave = new BigInteger(input.readUTF());
                        valor = input.readUTF();
                    }else if( tipo.equals("READ")  || tipo.equals("DELETE")) {
                    	escolha = 2;
                        chave = new BigInteger(input.readUTF());
                     
                    }else {
                    	erro = true;
                    	System.out.println("ERRO INESPERADO");
                    	out.writeUTF("erro e entrada de dados.");
                        break;
                    }
                    
                                    ItemFila justProduced = createItemFila();
                    queue.put(justProduced);
                    
               
                        }
                        catch(Exception e) {
                            System.out.println("Erro: " + e.getMessage());
                            break;
                        }
                    }
                    
                    System.out.println("* Conexão Finalizada *");
                    input.close();
                              
                }
                catch(Exception e) {
                    System.out.println("Erro TRY: " + e.getMessage());
                }
            }
            
            ItemFila createItemFila(){
                        ItemFila item = ( escolha == 1) ? new ItemFila(socketClient, operacao, chave, valor) : new ItemFila(socketClient, operacao, chave);
                        return item;
                    }
                }