package com.SDgroup;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

class EntryPoint implements Runnable
{
    protected BlockingQueue<ItemFila> queue;
    private int id = 0;
    private Socket sock;

    EntryPoint(Socket sock) {
        this.sock = sock;
        this.queue = F1.getInstance();
    }
    
    public void run()
    {
        try
        {
            while(true){
                ItemFila justProduced = getItemFila();
                queue.put(justProduced);
            }
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
    
    ItemFila getItemFila(){
        try{
            Thread.sleep(100); // simulate time passing during read
        }
        catch (InterruptedException ex){
           ex.printStackTrace();
        }
        ItemFila item = new ItemFila();
        item.k = this.id ++;
        item.v = this.id;
        item.socket = this.sock;
        return item;
    }
}