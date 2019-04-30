// package com.SDgroup;

import java.net.Socket;
import java.math.*;

public class ItemFila{
  Socket socket;
  byte[] controll;
  byte[] key;
  byte[] value;
  
  public ItemFila( Socket socket, byte[] controll, byte[] key, byte[] value) {
    this.socket = socket;
    this.controll = controll;
    this.key = key;
    this.value = value;
  }
  
  public ItemFila( Socket socket, byte[] controll, byte[] key) {
    this.socket = socket;
    this.controll = controll;
    this.key = key;
  }
  
  public void print() {
    String x = new String(controll);
    BigInteger y = new BigInteger(key);
    if( value != null ) {
      String z = new String(value);
      System.out.println( x + " " + y + " " + z);
    }
    else {
      System.out.println( x + " " + y);
    }
  }
}   