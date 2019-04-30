// package com.SDgroup;

import java.util.concurrent.BlockingQueue;
import java.io.*;
import java.net.*;
import java.util.*;
import java.math.BigInteger;

class EntryPoint implements Runnable
{
  protected BlockingQueue<ItemFila> queue;
  private int id = 0;
  private Socket socketClient;
  private byte[] controll;
  private byte[] key;
  private byte[] value;
  boolean everything;
  // private Socket sock;
  
  EntryPoint(Socket socketClient) {
    this.socketClient = socketClient;
    this.queue = F1.getInstance();
  }
  
  public void run() {
    int lengthWrong;
    int lengthRight;
    int type;
    boolean error;
    
    try {
      System.out.println("Cliente conectado: " + socketClient.getInetAddress().getHostAddress());
      
      DataInputStream input = new DataInputStream(socketClient.getInputStream());
      
      while(true) {
        try {
          error = true;
          everything = false;
          
          //LENDO COMANDO
          lengthWrong = input.readInt();
          lengthRight = (int) lengthWrong/10;
          type = lengthWrong - (lengthRight*10);
          
          if( lengthRight > 0 ) {
            controll = new byte[lengthRight];
            input.readFully(controll, 0, lengthRight);
          }
          
          if( type == 5 ) break;
          everything = ( type == 4 ) ? true : false;
          
          error = ( type == 1 || type == 4 ) ? error : false;
          
          //LENDO KEY
          lengthWrong = input.readInt();
          lengthRight = (int) lengthWrong/10;
          type = lengthWrong - (lengthRight*10);
          
          if( lengthRight > 0 ) {
            key = new byte[lengthRight];
            input.readFully(key, 0, lengthRight);
          }
          
          error = ( type == 2 ) ? error : false;
          
          //LENDO VALUE SE EXISTE
          if( everything ) {
            lengthWrong = input.readInt();
            lengthRight = (int) lengthWrong/10;
            type = lengthWrong - (lengthRight*10);
            
            if( lengthRight > 0 ) {
              value = new byte[lengthRight];
              input.readFully(value, 0, lengthRight);
            }
            
            error = ( type == 3 ) ? error : false;
          }
          
          if( !error ) {
            System.out.println("ERRO INESPERADO");
            break;
          }
          
          //COLOCA NA FILA AKI, POR ENQUANTO SO VOU PRINTAR
          ItemFila justProduced = createItemFila();
          queue.put(justProduced);
          
          // String x = new String(controll);
          // BigInteger y = new BigInteger(key);
          // if( everything ) {
          //   String z = new String(value);
          //   System.out.println( x + " " + y + " " + z);
          // }
          // else {
          //   System.out.println( x + " " + y);
          // }
        }
        catch(Exception e) {
          System.out.println("Erro: " + e.getMessage());
          break;
        }
      }
      
      System.out.println("Conexão Finalizada!!!");
      input.close();
      // socketClient.close();
      
    }
    catch(Exception e) {
      System.out.println("Erro TRY: " + e.getMessage());
    }
  }
  
  ItemFila createItemFila(){
    // try{
    //   Thread.sleep(100); // simulate time passing during read
    // }
    // catch (InterruptedException ex){
    //    ex.printStackTrace();
    // }
    
    ItemFila item = ( everything ) ? new ItemFila(socketClient, controll, key, value) : new ItemFila(socketClient, controll, key);
    return item;
  }
}