package state_machine.server;


import state_machine.type.Item;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.BlockingQueue;
import java.util.*;
import java.math.BigInteger;

public class Logger  implements Runnable
{
    private BlockingQueue<Item> f2;
    private BlockingQueue<Item> f3;
    private Path  path = Paths.get("./log");

    Logger(){
        this.f2 = F2.getInstance();
        this.f3 = F3.getInstance();
    }

    @Override
    public void run() {
        try{
            getListOfCommands();
            while (true) {
                Item obj = f2.take();
                writeCommand(obj);
            }
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    private void writeCommand(Item item){
        if ( item.getControll().equals("READ") )
            return;
        try {
            if(!Files.exists(path)) {
                System.out.println("Arquivo Inexistente, Criando...");
                Files.createFile(path);
            }
            Files.write(Paths.get("log"), (item.toString() + "\n").getBytes(), StandardOpenOption.APPEND);

        }catch (IOException e) {
            System.out.println("IO Error");
        }
    }

    private void getListOfCommands(){
        if(!Files.exists(path))
            return;
        List<String> contents;
        Item item;
        try{
            contents = Files.readAllLines(path);
            for(String content:contents){
                String[] commandSplited = content.split("\\s+");
                if ( commandSplited.length == 3 ){
                    item = new Item( commandSplited[0], new BigInteger( commandSplited[1] ), commandSplited[2]);
                    f3.add(item);
                }
                else if ( commandSplited.length == 2 ){
                    item = new Item( commandSplited[0], null, commandSplited[1]);
                    f3.add(item);
                }
            }
        }catch(Exception e){
            System.out.println( "Error: " + e.getMessage() );
        }
        F1.setFree();
    }
}