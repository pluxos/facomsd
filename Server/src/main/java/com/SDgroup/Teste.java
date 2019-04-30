package com.SDgroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Escrita implements Runnable {
    private int i;
    public void run() {
    while(true)
    System.out.println("Número: "+ i++);
    }
   }
    
    
    
   public class Teste {
    public static void main(String[] args) {
        List<ItemFila> row = (new LinkedList<ItemFila>());

        if(row instanceof LinkedList){
            System.out.println("é um Linkedlist!!!");
        }
        else System.out.println("naõ é");
        System.out.println(row.getClass());
  /*       Escrita e = new Escrita(); //Cria o contexto de execução
    Thread t = new Thread(e); //Cria a linha de execução
    t.start(); //Ativa a thread
    new Thread(e).start();
 */    }
   }