package org.kim.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class Main {

    private static int port;

    public static void main(String[] args) {

        serverIdentify();

        try {
            Server server = ServerBuilder.forPort(port)
                    .addService(new ServiceImpl())
                    .build();

            System.out.println("Starting server...");
            server.start();
            System.out.println("Server started!");
            server.awaitTermination();
        }
        catch (IOException | InterruptedException e) { System.out.println("Server error: " + e.getMessage()); }
    }

    private static void serverIdentify() {

        String fileName = "C:\\Users\\Kim\\Desktop\\trab\\facomsd\\trabalho2\\src\\main\\java\\org\\kim\\grpc\\server\\servers_routes.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String last = ";0000", line;
            String[] v = null;

            while ((line = br.readLine()) != null) {
                last = line;
            }

            if (last != null) v = last.split(";");

            if (v != null && v[1].equals("8080")) {
                System.out.println("Primeiro servidor já foi iniciado");
                port = 8081;

                Files.write(Paths.get(fileName), "\n".getBytes(), StandardOpenOption.APPEND);

                Files.write(Paths.get(fileName), "Server;2".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(fileName), "\n".getBytes(), StandardOpenOption.APPEND);

                Files.write(Paths.get(fileName), ("Port;" + port).getBytes(), StandardOpenOption.APPEND);
            }

            else if (v != null && v[1].equals("8081")) {
                System.out.println("Segundo servidor já foi iniciado");
                port = 8082;

                Files.write(Paths.get(fileName), "\n".getBytes(), StandardOpenOption.APPEND);

                Files.write(Paths.get(fileName), "Server;3".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(fileName), "\n".getBytes(), StandardOpenOption.APPEND);

                Files.write(Paths.get(fileName), ("Port;" + port).getBytes(), StandardOpenOption.APPEND);
            }

            else if (v != null && v[1].equals("8082")) {
                System.out.println("Terceiro servidor já foi iniciado");
                port = 8083;

                Files.write(Paths.get(fileName), "\n".getBytes(), StandardOpenOption.APPEND);

                Files.write(Paths.get(fileName), "Server;4".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(fileName), "\n".getBytes(), StandardOpenOption.APPEND);

                Files.write(Paths.get(fileName), ("Port;" + port).getBytes(), StandardOpenOption.APPEND);
            }

            else if (v != null && v[1].equals("8083")) {
                System.out.println("Quarto servidor já foi iniciado");
                port = 8084;

                Files.write(Paths.get(fileName), "\n".getBytes(), StandardOpenOption.APPEND);

                Files.write(Paths.get(fileName), "Server;5".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(fileName), "\n".getBytes(), StandardOpenOption.APPEND);

                Files.write(Paths.get(fileName), ("Port;" + port).getBytes(), StandardOpenOption.APPEND);
            }

            else {
                port = 8080;

                Files.write(Paths.get(fileName), "Server;1".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(fileName), "\n".getBytes(), StandardOpenOption.APPEND);

                Files.write(Paths.get(fileName), ("Port;" + port).getBytes(), StandardOpenOption.APPEND);
            }
        }
        catch (IOException e) { System.out.println("Server error: " + e.getMessage()); }
    }
}
