package threads;

import server.ItemFila;
import server.Table;
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
    String pathString = new String();
    int number;

    public Logger() {
        pathString += Table.getInstance().getMyKey();
        pathString += "/log.";
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
                        if (Files.exists(path))
                            Files.deleteIfExists(path);
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
            if (Files.exists(path))
                Files.deleteIfExists(path);
            Files.createFile(path);
            comando = comando + "\n";
            Files.write(path, comando.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("IO Error");
        }
    }

    /* Essa é a parte de recuperação apartir do arquivo log e snapshot */
    public void getListOfCommands() {
        Banco.getInstance().blockDatabase();
        int lastSnapNumber = 0;
        try (Stream<Path> walk = Files.walk(Paths.get(Integer.toString(Table.getInstance().getMyKey())))) {
            List<String> files = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
            if (files.size() == 0)
                return;

            List<String> snaps = files.stream().filter(file -> file.matches(Table.getInstance().getMyKey() + "/snap.*")).collect(Collectors.toList());
            if (snaps.size() > 0) {
                lastSnapNumber = Integer.parseInt(snaps.get(0).substring( snaps.get(0).lastIndexOf('.') + 1 ));
                // Captura o último snapshot
                for (String s : snaps)
                    if (Integer.parseInt(s.substring(s.lastIndexOf('.')+1)) > lastSnapNumber)
                        lastSnapNumber = Integer.parseInt(s.substring(s.lastIndexOf('.')+1));
                // Restaurando todo o snap
                for (String s : Files.readAllLines(Paths.get(Table.getInstance().getMyKey() + "/snap." + lastSnapNumber))){
                    Banco.getInstance().Insert(s);

                }
            }

            List<String> logs = files.stream().filter(file -> file.matches(Table.getInstance().getMyKey() + "/log.*")).collect(Collectors.toList());
            if (logs.size() > 0) {
                // Removendo os logs que são anteriores ao último snapshot
                for (int i = 0; i < logs.size(); i++)
                    if (Integer.parseInt(logs.get(i).substring(logs.get(i).lastIndexOf('.') + 1)) < lastSnapNumber)
                        logs.remove(i);

                List<String> contents;
                ItemFila item;
                Path path2;
                for (String s : logs) {
                    path2 = Paths.get(s);
                    try {
                        contents = Files.readAllLines(path2);
                        for (String content : contents) {
                            String[] commandSplited = content.split("\\s+");
                            item = new ItemFila();
                            if (commandSplited.length == 3) {
                                if (commandSplited[0].equals("CREATE")) {
                                    item.itemFilaCreate(null, new BigInteger(commandSplited[1]).toByteArray(),
                                            commandSplited[2].getBytes());
                                } else if (commandSplited[0].equals("UPDATE")) {
                                    item.itemFilaUpdate(null, new BigInteger(commandSplited[1]).toByteArray(),
                                            commandSplited[2].getBytes());
                                }
                                f3.add(item);
                            } else if (commandSplited.length == 2) {
                                if (commandSplited[0].equals("DELETE")) {
                                    item.itemFilaDelete(null, new BigInteger(commandSplited[1]).toByteArray());
                                } else if (commandSplited[0].equals("READ")) {
                                    item.itemFilaRead(null, new BigInteger(commandSplited[1]).toByteArray());
                                }
                                f3.add(item);
                            } else
                                System.out.println("Comando desconhecido");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro na gravação dos dados: " + e);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Banco.getInstance().freeDatabase();
        F1.setFree();
    }
}