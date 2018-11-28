package org.kim.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadMain extends Thread {

    private static final int QUEUE_SIZE = 2048;

    public static DataStorage dataStorage = new DataStorage();

    private static int port;
    private Server server;

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

    private void starter() {
        BlockingQueue<Command> F1 = new ArrayBlockingQueue<>(QUEUE_SIZE);
        BlockingQueue<Command> F2 = new ArrayBlockingQueue<>(QUEUE_SIZE);
        BlockingQueue<Command> F3 = new ArrayBlockingQueue<>(QUEUE_SIZE);
        BlockingQueue<Command> F4 = new ArrayBlockingQueue<>(QUEUE_SIZE);

        serverIdentify();

        try {
            // Passa comando do cliente para F1
            server = ServerBuilder.forPort(port)
                    .addService(new ServiceImpl(F1))
                    .build();

            System.out.println("Starting server...");
            server.start();
            System.out.println("Server started at port " + port);

            // Passa os comandos para as filas F2, F3 e/ou F4
            ThreadQueueProcess threadQueueProcess = new ThreadQueueProcess(port, F1, F2, F3, F4);
            threadQueueProcess.start();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                    System.err.println("*** shutting down gRPC server since JVM is shutting down");
                    ThreadMain.this.stoper();
                    System.err.println("*** server shut down");
                }
            });
        }
        catch (IOException e) { System.out.println("Server error: " + e.getMessage()); }
    }

    private void stoper() { if (server != null) server.shutdown(); }

    private void blockUntilShutdown() throws InterruptedException { if (server != null) server.awaitTermination(); }

    @Override
    public void run(){
        while (true) {
            final ThreadMain server = new ThreadMain();
            server.starter();

            try {
                server.blockUntilShutdown();
            }
            catch (InterruptedException e) { System.out.println("Server error: " + e.getMessage()); }
        }
    }
}
