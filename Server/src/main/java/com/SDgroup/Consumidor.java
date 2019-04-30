package com.SDgroup;

public class Consumidor implements Runnable {
    private F1 f1;
    
    Consumidor() {
        f1 = F1.getInstance();
        
    }
    
    @Override
    public void run() {
        ItemFila item = null;;
        while(true){
            try {
                item = f1.unqueue();
            } catch (Exception e) {
                try {
                    f1.wait();
                    continue;
                } catch (InterruptedException e1) {
                    
                    e1.printStackTrace();
                }
            }
            System.out.println("O chave do item obtido foi: " + item.k);
        }
    }
    
}