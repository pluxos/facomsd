package com.SDgroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Consumidor implements Runnable{
    private Collection<Comando> F1;
    private Collection<Comando> F2;
    private Collection<Comando> F3;
    Consumidor(){
        F1 = Collections.synchronizedCollection(new ArrayList<Comando>());
        F2 = Collections.synchronizedCollection(new ArrayList<Comando>());
        F3 = Collections.synchronizedCollection(new ArrayList<Comando>());
    }
    @Override
    public void run() {
        F1.
    }
    
}