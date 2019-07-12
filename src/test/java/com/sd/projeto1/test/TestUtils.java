/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.test;

import com.sd.projeto1.main.Client;
import com.sd.projeto1.main.Server;
import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rcunhare
 */
public class TestUtils {
    
    public static void recuperacaoEstadoTest() throws Exception{
        Server server = new Server();
        
        server.main("0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002".split(" "));
        
        Client client = new Client();
        
        client.isTest = true;
        
        client.main("127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002".split(" "));
        
        for(long i = 0; i < 5 ; i++){
            client.insertTest(i, "" + (i+1));
        }
        
        try (BufferedReader br = Files.newBufferedReader(Paths.get("app.log"))) {
            String line;
            long i = 0;
            while ((line = br.readLine()) != null) {
                assertEquals("1#"+i+"#"+(i+1),line);
                i++;
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
            "Finalizado com sucesso!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    
    public static void ordemExecucaoTest() throws Exception {
        Server server = new Server();
        
        server.main("0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002".split(" "));
        
        Client client = new Client();
        
        client.isTest = true;
        
        client.main("127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002".split(" "));
        
        client.insertTest(0,"1");
        
        for(long i = 1; i <= 1000 ; i++){
            client.insertTest(i, "" + (i+1) );
        }
        
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        client.getKeyTest(1000);
        System.out.println("Finalizado com sucesso!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    
    public static void concorrenciaTest() throws Exception {
        Server server = new Server();
        
        server.main("0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002".split(" "));
        
        Client client = new Client();
        ArrayList<Client> lstClients = new ArrayList<Client>();
        String parameters = "127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002";
        
        for(int i = 0; i < 10 ; i++){
            Client c = new Client();
            c.isTest = true;
            c.main(parameters.split(" "));
            lstClients.add(c);
        }
        
        long i = 0;
        for( Client c :lstClients ){
            Thread t = new TestUtils.ThreadInsertClient(c,i);
            t.start();
            i += 20;
        }
        
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Finalizado com sucesso!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    
    public static class ThreadInsertClient extends Thread implements Runnable{
        static Client c;
        static long inicio;
        public ThreadInsertClient(Client c,long inicio){
            this.c = c;
            this.inicio = inicio;
        }
        @Override
        public void run() {
            try {
                for(long i = inicio; i < (10+inicio) ; i++){
                    c.insertTest(i, ""+i );
                }
            } catch (Exception e) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
