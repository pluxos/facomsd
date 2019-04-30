// package com.SDgroup;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.BlockingQueue;

public class Logger  implements Runnable
{

    protected BlockingQueue<ItemFila> f2;
    Path  path = Paths.get("./log");

    Logger(){
        this.f2 = F2.getInstance();
    }

    @Override
    public void run() {
        try{
            while (true){
                ItemFila obj = f2.take();
                writeCommand(obj.toString());
            }
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    private void writeCommand(String comando){
        // if(comando == null) System.out.println("MEGA BATATA");
        if ( comando.substring( 0, 4 ).equals("READ") )
            return;
        try {
            if(!Files.exists(path)) {
                System.out.println("Arquivo Inexistente, Criando...");
                Files.createFile(path);
            }
            comando = comando + "\n";
            Files.write(Paths.get("log"), comando.getBytes(), StandardOpenOption.APPEND);

        }catch (IOException e) {
            System.out.println("IO Error");
        }
    }



    /*Essa é a parte de recuperação apartir do arquivo log*/
//    public List<String> getListOfCommands(){
//        List<String> contents;
//        try{
////            Path  path = Paths.get("./log");
//            contents = Files.readAllLines(path);
//            return contents;
//        }catch(IOException ex){
//            ex.printStackTrace();//handle exception here
//        }
//        return null;
//    }

//    public static void main(String[] args) throws Exception {
//        writeCommand("comando maluco5");
//        writeCommand("READ alkfdjalskdfjç");
//        List<String> s = getListOfCommands();
//        for(String content:s){//for each line of content in contents
//            System.out.println(content);// print the line
//        }
//    }
}