// package com.SDgroup;

import java.util.concurrent.BlockingQueue;

class Consumidor implements Runnable
{
  protected BlockingQueue<ItemFila> f1;
  protected BlockingQueue<ItemFila> f2;
  protected BlockingQueue<ItemFila> f3;
  
  Consumidor() {
    this.f1 = F1.getInstance();
    this.f2 = F2.getInstance();
    this.f3 = F3.getInstance();
  }
  
  public void run() {
    try{
      while (true){
        ItemFila obj = f1.take();
        obj.print();
        f2.put(obj);
        f3.put(obj);
      }
    }
    catch (InterruptedException ex){
      ex.printStackTrace();
    }
  }
}