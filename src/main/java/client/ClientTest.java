package client;

import client.controller.Client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientTest {
    public static void main(String[] args) {
        System.out.println("Bem vindo ao cliente de teste!");

        ExecutorService pool = Executors.newFixedThreadPool(10);

        String[] clientArgs = {"teste", "src/test/resources/stress.txt"};

        for (int i = 0; i < 10; i ++) {
            pool.execute(new Client(clientArgs));
        }
    }
}
