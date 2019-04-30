package com.SDgroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Rows {
    private Collection<Comando> row;
    private int head, tail;
    
    private static class MyWrapper {
        static Rows INSTANCE = new Rows();
    }
    
    private Rows () {
        head = 0;
        tail = 0;
        row = Collections.synchronizedCollection(new ArrayList<Comando>());
    }
    
    public static Rows getInstance() {
        return MyWrapper.INSTANCE;
    }
    
    public void queue(Comando c){
        row.add(c);
    }
    
    public Comando unqueue(){
        Thread.currentThread().
        return row.
    }
}