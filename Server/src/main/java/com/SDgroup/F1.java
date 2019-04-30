package com.SDgroup;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class F1 {
    private List<ItemFila> row;
    private int head, tail;
    
    private static class MyWrapper {
        static F1 INSTANCE = new F1();
    }
    
    private F1 () {
        head = 0;
        tail = 0;
        row = Collections.synchronizedList(new LinkedList<ItemFila>());
    }
    
    public static F1 getInstance() {
        return MyWrapper.INSTANCE;
    }
    
    public synchronized void queue(ItemFila c){
        row.add(head, c);
        head++;
    }
    
    public synchronized ItemFila unqueue()throws NoSuchElementException{
        if( tail == head) {
        throw new NoSuchElementException();
        } 
        ItemFila itemFila = row.remove(tail);
        tail-=2;
        head--;
        return itemFila;
    }
}