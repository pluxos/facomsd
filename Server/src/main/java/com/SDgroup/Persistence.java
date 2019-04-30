// package com.SDgroup;

import java.util.concurrent.BlockingQueue;
import java.math.BigInteger;
import java.lang.*;
import java.io.*;
import java.net.*;


public class Persistence  implements Runnable {

    protected BlockingQueue<ItemFila> f3;
    private Kv Database = new Kv();

    public Persistence () {
        this.f3 = F3.getInstance();
    }

    @Override
    public void run () {
        String callback = null; // Mensagem de sucesso ou falha
        byte[] response;
        ItemFila obj;
        int type; // type == 1 string, type == 2 byte
        // int size;

        try {
            while (true) {
                response = null;
                obj = f3.take();
                type = 1;
                
                DataOutputStream output = new DataOutputStream( obj.socket.getOutputStream() );

                if (new String(obj.controll).equals("CREATE")) {
                    callback = ( Database.Insert(new BigInteger(obj.key), obj.value) ) ? "CREATE SUCESS!" : "CREATE FAIL!";
                }
                else if (new String(obj.controll).equals("UPDATE")) {
                    callback = ( Database.Update(new BigInteger(obj.key), obj.value) ) ? "UPDATE SUCESS!" : "UPDATE FAIL!";
                }
                else if (new String(obj.controll).equals("DELETE")) {
                    callback = ( Database.Delete(new BigInteger (obj.key) ) ) ? "DELETE SUCESS!" : "DELETE FAIL!";
                }
                else if (new String(obj.controll).equals("READ")) {
                    response = Database.Read(new BigInteger(obj.key));
                    callback = "READ FAIL!";
                    if( response != null ) {
                        type = 2;
                    }
                } else {
                    System.out.println("ERROR");
                }

                if( type == 1) {
                    byte[] messageBytesCommand = callback.getBytes();
                    output.writeInt( ( messageBytesCommand.length*10 + type ) );
                    output.write( messageBytesCommand );
                } else  if (type == 2) {
                    output.writeInt( ( response.length*10 + type ) );
                    output.write( response );
                } else {
                    System.out.println("ERROR");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
