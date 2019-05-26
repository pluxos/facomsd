package threads;

import model.ItemFila;
import singletons.*;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;
import java.math.BigInteger;

public class Logger implements Runnable {
    protected BlockingQueue<ItemFila> f1;
    protected BlockingQueue<ItemFila> f2;
    protected BlockingQueue<ItemFila> f3;
    Path path;
    String pathString = "./log.";
    int number;

    public Logger() {
        this.f1 = F1.getInstance();
        this.f2 = F2.getInstance();
        this.f3 = F3.getInstance();
        this.path = Paths.get(pathString + number);
    }

    @Override
    public void run() {
        try {
            getListOfCommands();
            while (true) {
                if (Banco.getInstance().isFree()) {
                    number = Banco.getInstance().getNumber() - 1;
                    path = Paths.get(pathString + number);
                    try {
                        if (!Files.exists(path))
                            Files.createFile(path);
                        if (number >= 3)
                            Files.deleteIfExists(Paths.get(pathString + (number - 3)));
                    } catch (IOException e) {
                        System.out.println("Erro na criação do logger: " + e.getMessage());
                    }

                    if (!f2.isEmpty()) {
                        ItemFila obj = f2.take();
                        writeCommand(obj.toString());
                    }
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Erro no logger: " + ex.getMessage());
        }
    }

    private void writeCommand(String comando) {
        
        if (comando.substring(0, 4).equals("READ"))
            return;
        try {
            if (!Files.exists(path)) {
                System.out.println("Arquivo Inexistente, Criando...");
                Files.createFile(path);
            }
            comando = comando + "\n";
            Files.write(path, comando.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("IO Error");
        }
    }

    /* Essa é a parte de recuperação apartir do arquivo log */
    public void getListOfCommands() {
        try (Stream<Path> walk = Files.walk(Paths.get("."))) {

            List<String> files = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

            List<String> logs = files.stream().filter(file -> file.matches("./log.*")).collect(Collectors.toList());
            List<String> snaps = files.stream().filter(file -> file.matches("./snap.*")).collect(Collectors.toList());
            Collections.sort(logs);
            Collections.sort(snaps);
            logs.forEach(System.out::println);
            snaps.forEach(System.out::println);
            // \./log.*|\./snap.*
        } catch (IOException e) {
            e.printStackTrace();
        }
        // List<String> contents;
        // ItemFila item;
        // try {
        //     contents = Files.readAllLines(path);
        //     for (String content : contents) {
        //         String[] commandSplited = content.split("\\s+");
        //         if (commandSplited.length == 3) {
        //             item = new ItemFila(null, commandSplited[0].getBytes(),
        //                     new BigInteger(commandSplited[1]).toByteArray(), commandSplited[2].getBytes());
        //             f3.add(item);
        //         } else if (commandSplited.length == 2) {
        //             item = new ItemFila(null, commandSplited[0].getBytes(),
        //                     new BigInteger(commandSplited[1]).toByteArray());
        //             f3.add(item);
        //         }
        //     }
        // } catch (Exception e) {
        //     System.out.println("Erro na gravação dos dados: " + e);
        // }
        // F1.setFree();
    }
}