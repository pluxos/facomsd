package state_machine.server;

import state_machine.type.Item;

import java.util.concurrent.BlockingQueue;
import java.io.*;
import java.net.*;
import java.util.*;

class Consumidor implements Runnable
{
    protected BlockingQueue<Item> f1;
    protected BlockingQueue<Item> f2;
    protected BlockingQueue<Item> f3;

    Consumidor() {
        this.f1 = F1.getInstance();
        this.f2 = F2.getInstance();
        this.f3 = F3.getInstance();
    }

    public void run() {
        try{
            while (true){
                Item obj = f1.take();
                f2.put(obj);
                f3.put(obj);
            }
        }
        catch (Exception e){
            System.out.println( "Erro: " + e.getMessage() );
        }
    }
}