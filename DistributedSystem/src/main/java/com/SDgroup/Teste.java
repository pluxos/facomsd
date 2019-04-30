package com.SDgroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

class Escrita implements Runnable {
    private int i;
    public void run() {
    while(true)
    System.out.println("Número: "+ i++);
    }
   }
    
    
    
   public class Teste {
    public static void main(String[] args) {
         Collection<Comando>  F1 = Collections.synchronizedCollection(new ArrayList<Comando>());

        if(F1 instanceof ArrayList){
            System.out.println("!é um arraylist");
        }
        else System.out.println("naõ é");
        System.out.println(F1.getClass());
        Escrita e = new Escrita(); //Cria o contexto de execução
    Thread t = new Thread(e); //Cria a linha de execução
    t.start(); //Ativa a thread
    new Thread(e).start();
    }
   }