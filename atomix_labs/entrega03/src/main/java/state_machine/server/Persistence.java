package state_machine.server;
import state_machine.type.Item;

import java.util.concurrent.BlockingQueue;
import java.math.BigInteger;
import java.lang.*;
import java.io.*;
import java.net.*;


public class Persistence  implements Runnable {

    protected BlockingQueue<Item> f3;
    private Kv Database = new Kv();

    public Persistence () {
        this.f3 = F3.getInstance();
    }

    @Override
    public void run () {
        Item obj;
        try {
            while (true) {
                obj = f3.take();
                if (obj.getControll().equals("CREATE")) {
                    Database.Insert( obj.getKey(), obj.getValue() );
                }
                else if (obj.getControll().equals("UPDATE")) {
                    Database.Update( obj.getKey(), obj.getValue() );
                }
                else if (obj.getControll().equals("DELETE")) {
                    Database.Delete( obj.getKey() );
                }
                else if (obj.getControll().equals("READ")) {
                    Database.Read( obj.getKey() );
                } else {
                    System.out.println("Comando não reconhecido!!");
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}