package com.SDgroup;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Logger {

    Path  path = Paths.get("./log");

    public void writeCommand(String comando){
        if (comando.contains("READ"))
            return;
        try {
//            Path  path = Paths.get("./log");
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

    public List<String> getListOfCommands(){
        List<String> contents;
        try{
//            Path  path = Paths.get("./log");
            contents = Files.readAllLines(path);
            return contents;
        }catch(IOException ex){
            ex.printStackTrace();//handle exception here
        }
        return null;
    }

//    public static void main(String[] args) throws Exception {
//        writeCommand("comando maluco5");
//        writeCommand("READ alkfdjalskdfjç");
//        List<String> s = getListOfCommands();
//        for(String content:s){//for each line of content in contents
//            System.out.println(content);// print the line
//        }
//    }
}