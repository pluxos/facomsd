/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package server;

import java.math.BigInteger;

import com.google.protobuf.GeneratedMessageV3;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import java.lang.String;
import java.lang.Integer;
import com.google.protobuf.ByteString;
import server.ItemFila;
import singletons.F1;
import threads.*;
import server.Table;
import server.ChordGrpc.ChordImplBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.Scanner;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class ServerGrpc {
    private static final BlockingQueue<ItemFila> f1 = F1.getInstance();
    private static Table table;
    private static final Logger logger = Logger.getLogger(ServerGrpc.class.getName());

    private Server server;
    public Map<BigInteger, byte[]> Database = new HashMap<BigInteger, byte[]>();

    private void start() throws IOException {
        /* The port on which the server should run */
        Scanner scanner = new Scanner( System.in );

        System.out.println("Digite uma porta:");
        String scan = scanner.nextLine();
        int myPort = Integer.parseInt(scan);
        
        System.out.println("Digite o número do servidor:");
        scan = scanner.nextLine();
        int myKey = Integer.parseInt(scan);

        System.out.println("Digite a porta de um servidor no Chords:");
        scan = scanner.nextLine();
        int friendPort = Integer.parseInt(scan);

        String myIP = "localhost";
        String friendIP = "localhost";

        Table.createInstance(myKey, myIP, myPort);
        Table tabela = Table.getInstance();
        ServerGrpc.table = tabela;

        scanner.close();

        if (friendPort != myPort) {
            // Adicionar esse servidor no Chord
            new Thread( new AddServer(friendIP, friendPort, myKey, myIP, myPort, tabela) ).start();
        }

        server = ServerBuilder.forPort(myPort)
                .addService(new CrudImpl(this.Database))
                .addService(new ChordImpl())
                .build()
                .start();

        logger.info("Server started, listening on " + myPort);
        new Thread(new threads.Logger()).start();
        new Thread(new Consumidor()).start();
        new Thread(new Persistence()).start();
        Timer t = new Timer();
        t.scheduleAtFixedRate(new server.Snap(), 0, 1000);
        new Thread(new F4Consumer()).start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown
                // hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                ServerGrpc.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final ServerGrpc server = new ServerGrpc();
        server.start();
        server.blockUntilShutdown();

    }

    static class CrudImpl extends CrudGrpc.CrudImplBase {
        Map<BigInteger, byte[]> Database;

        CrudImpl(Map<BigInteger, byte[]> pai) {
            this.Database = pai;
        }

        @Override
        public void create(CreateRequest req, StreamObserver<CreateResponse> responseObserver) {
            byte[] chave = new byte[req.getKeysize()];
            ItemFila itemfila = new ItemFila();
            byte[] value = new byte[req.getValuesize()];
            req.getKey().copyTo(chave, 0);
            req.getValue().copyTo(value, 0);
            
            itemfila.itemFilaCreate(responseObserver, chave, value);

            String nextIP = table.table[0][2];
            int nextPort = Integer.parseInt(table.table[0][3]);

            // System.out.println("CREATE: < " + new BigInteger(chave) + " , " + new String(value) + " >");
            new Thread( new SearchFirst(nextIP, nextPort, itemfila) ).start();
        }

        @Override
        public void read(ReadRequest req, StreamObserver<ReadResponse> responseObserver) {
            byte[] chave = new byte[req.getKeysize()];
            ItemFila itemfila = new ItemFila();
            req.getKey().copyTo(chave, 0);
            System.out.println("READ: < " + new BigInteger(chave) + " >");

            itemfila.itemFilaRead(responseObserver, chave);

            String nextIP = table.table[0][2];
            int nextPort = Integer.parseInt(table.table[0][3]);
            
            new Thread( new SearchFirst(nextIP, nextPort, itemfila) ).start();
            // try {
            //     f1.put(itemfila);
            // } catch (Exception e) {
            //     System.out.println("Erro: " + e.getMessage());
            // }
        }

        @Override
        public void update(UpdateRequest req, StreamObserver<UpdateResponse> responseObserver) {
            byte[] chave = new byte[req.getKeysize()];
            ItemFila itemfila = new ItemFila();
            byte[] value = new byte[req.getValuesize()];
            req.getKey().copyTo(chave, 0);
            req.getValue().copyTo(value, 0);

            // System.out.println("UPDATE: < " + new BigInteger(chave) + " , " + new String(value) + " >");
            itemfila.itemFilaUpdate(responseObserver, chave, value);

            String nextIP = table.table[0][2];
            int nextPort = Integer.parseInt(table.table[0][3]);
            
            new Thread( new SearchFirst(nextIP, nextPort, itemfila) ).start();

            // try {
            //     f1.put(itemfila);
            // } catch (Exception e) {
            //     System.out.println("Erro: " + e.getMessage());
            // }
        }

        @Override
        public void delete(DeleteRequest req, StreamObserver<DeleteResponse> responseObserver) {
            byte[] chave = new byte[req.getKeysize()];
            ItemFila itemfila = new ItemFila();
            req.getKey().copyTo(chave, 0);
            // System.out.println("DELETE: < " + new BigInteger(chave) + " >");

            itemfila.itemFilaDelete(responseObserver, chave);

            String nextIP = table.table[0][2];
            int nextPort = Integer.parseInt(table.table[0][3]);

            new Thread( new SearchFirst(nextIP, nextPort, itemfila) ).start();

            // try {
            //     f1.put(itemfila);
            // } catch (Exception e) {
            //     System.out.println("Erro: " + e.getMessage());
            // }
        }

    }
    static class ChordImpl extends ChordImplBase {
        
        @Override
        public void addServidor(AddRequest req, StreamObserver<AddResponse> responseObserver) {
            
            // System.out.println("ADDSERVER: < " + req.getKey() + " - " + req.getIp() + " - " + req.getPort()  + " >");

            boolean needResponse = true;

            int myKey = table.myKey;
            String myIP = table.myIP;
            int myPort = table.myPort;

            int serverKey = req.getKey();
            String serverIP = req.getIp();
            int serverPort = req.getPort();
            
            int nextKey = Integer.parseInt(table.table[0][1]);
            String nextIP = table.table[0][2];
            int nextPort = Integer.parseInt(table.table[0][3]);

            int responseKey = 0;
            String responseIP = "ERROR";
            int responsePort = 0;

            
            if (myKey == serverKey) {
                System.out.println("ERRO!!! SERVIDOR JÁ EXISTENTE ");
            }
            else if (nextKey == 0) {
                table.setTable(serverKey, serverIP, serverPort);
                responseKey = myKey;
                responseIP = myIP;
                responsePort = myPort;
            }
            else if (serverKey > myKey && (serverKey < nextKey || nextKey < myKey)) {
                table.insertTable(serverKey, serverIP, serverPort, 0);
                responseKey = nextKey;
                responseIP = nextIP;
                responsePort = nextPort;
            }
            else if (nextKey < myKey && serverKey < nextKey) {
                table.insertTable(serverKey, serverIP, serverPort, 0);
                responseKey = nextKey;
                responseIP = nextIP;
                responsePort = nextPort;
            }
            else {
                needResponse = false;

                // Se entrou aki é necessário repassar para o proximo
                // if (first) {
                //     needResponse = false;
                // }
                // else {
                //     responseKey = -1;
                //     responseIP = "NEXT";
                //     responsePort = -1;
                // }
                // RETIRAR TABLE DEPOIS, ELA É ESTATICA
                new Thread( new AddServer(nextIP, nextPort, serverKey, serverIP, serverPort, table, responseObserver) ).start();
            }

            if (needResponse) {
                AddResponse response = AddResponse.newBuilder().setIp(responseIP).setPort(responsePort).setKey(responseKey).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        }
    
        @Override
        public void deleteServidor(DelRequest req, StreamObserver<DelResponse> responseObserver) {
            // System.out.println("DELSERVER: < " + req.getKey() + " - " + req.getIp() + " - " + req.getPort()  + " >");

            int myKey = table.myKey;
            String myIP = table.myIP;
            int myPort = table.myPort;

            int serverKey = req.getKey();
            
            int nextKey = Integer.parseInt(table.table[0][1]);
            String nextIP = table.table[0][2];
            int nextPort = Integer.parseInt(table.table[0][3]);

            if(myKey != serverKey) {
                for (int i = 0; i < table.table.length; i++) {
                    int expKeyX = Integer.parseInt(table.table[i][0]);
                    int keyX = Integer.parseInt(table.table[i][1]);

                    if (keyX == serverKey) {
                        new Thread( new UpdateServer(nextIP, nextPort, myKey, expKeyX, i) ).start();
                    }
                }
                // CONTINUAR ATUALIZANDO
                new Thread( new DeleteServer(nextIP, nextPort, serverKey) ).start();
            }
            else {
                // FECHAR SERVIDOR DEPOIS DE DEIXAR A LISTA VAZIA
            }

            DelResponse response = DelResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    
        @Override
        public void notifyServidor(NotifyRequest req, StreamObserver<NotifyResponse> responseObserver) {

            int myKey = table.myKey;
            String myIP = table.myIP;
            int myPort = table.myPort;

            int serverKey = req.getNewkey();
            String serverIP = req.getNewip();
            int serverPort = req.getNewport();
            
            int nextKey = Integer.parseInt(table.table[0][1]);
            String nextIP = table.table[0][2];
            int nextPort = Integer.parseInt(table.table[0][3]);

            if(myKey != serverKey) {

                for (int i = 1; i < table.table.length; i++) {
                    int expKeyX = Integer.parseInt(table.table[i][0]);
                    int keyX = Integer.parseInt(table.table[i][1]);

                    //SHOW DE IFs
                    if (myKey > nextKey) {
                        expKeyX = expKeyX - myKey;
                    }

                    if (expKeyX == keyX) {
                        continue;
                    }
                    else if (expKeyX == serverKey) {
                        table.insertTable(serverKey, serverIP, serverPort, i);
                        continue;
                    }
                    else if (expKeyX < serverKey && serverKey < keyX) {
                        table.insertTable(serverKey, serverIP, serverPort, i);
                        continue;
                    }
                    else if (expKeyX > keyX) {
                        // RECALCULAR
                        new Thread( new UpdateServer(nextIP, nextPort, myKey, expKeyX, i) ).start();
                        // new Thread( new UpdateServer(nextIP, nextPort, myKey, Integer.parseInt(table.table[i][0]), i) ).start();
                    }
                }

                if(nextKey != serverKey) {
                    // CONTINUAR ATUALIZANDO
                    new Thread( new NotifyAllServers(nextIP, nextPort, serverKey, serverIP, serverPort) ).start();
                }
                    
            }

            NotifyResponse response = NotifyResponse.newBuilder().setOk("OK").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void updateServidor(UpRequest req, StreamObserver<UpResponse> responseObserver) {
            // System.out.println("UPDATESERVER: < " + req.getKey() + " - " + req.getIp() + " - " + req.getPort()  + " >");
            
            boolean needResponse = true;

            int myKey = table.myKey;
            String myIP = table.myIP;
            int myPort = table.myPort;

            int serverKey = req.getMykey();
            int serverPosition = req.getPosition();
            int serverExpKey = req.getExpkey();
            
            int nextKey = Integer.parseInt(table.table[0][1]);
            String nextIP = table.table[0][2];
            int nextPort = Integer.parseInt(table.table[0][3]);

            int responseKey = 0;
            String responseIP = "ERROR";
            int responsePort = 0;

            if(nextKey == serverKey) {
                responseKey = myKey;
                responseIP = myIP;
                responsePort = myPort;
            }
            else if (myKey == serverKey) {
                System.out.println("NAO DEVIRIA ENTRAR AKI, NAO ACHOU CANDIDATO");
            }
            else if (myKey >= serverExpKey) {
                responseKey = myKey;
                responseIP = myIP;
                responsePort = myPort;
            }
            else if (myKey > nextKey && serverExpKey > myKey) {
                // BOLA PARA FRENTE POREM EXP KEY = -MYNUM
                needResponse = false;
                new Thread( new UpdateServer(nextIP, nextPort, serverKey, (serverExpKey - myKey), serverPosition, responseObserver) ).start();
            }
            else {
                // BOLA PARA FRENTE POREM EXP KEY = -MYNUM
                needResponse = false;
                new Thread( new UpdateServer(nextIP, nextPort, serverKey, serverExpKey, serverPosition, responseObserver) ).start();
            }

            if (needResponse) {
                UpResponse response = UpResponse.newBuilder().setIp(responseIP).setPort(responsePort).setKey(responseKey).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
            
        }
    
        @Override
        public void searchFirst(SearchRequest req, StreamObserver<SearchResponse> responseObserver) {
            // System.out.println("SEARCHFIRST: < " + req.getKey() + " - " + req.getIp() + " - " + req.getPort()  + " >");

            boolean needResponse = true;
            
            int myKey = table.myKey;
            String myIP = table.myIP;
            int myPort = table.myPort;

            int nextKey = Integer.parseInt(table.table[0][1]);
            String nextIP = table.table[0][2];
            int nextPort = Integer.parseInt(table.table[0][3]);

            int responseKey = 0;
            String responseIP = "ERROR";
            int responsePort = 0;

            if (nextKey == 0) {
                responseKey = myKey;
                responseIP = myIP;
                responsePort = myPort;
            }
            else if (myKey > nextKey) {
                responseKey = nextKey;
                responseIP = nextIP;
                responsePort = nextPort;
            }
            else {
                needResponse = false;
                new Thread( new SearchFirst(nextIP, nextPort, responseObserver) ).start();
            }

            if (needResponse) {
                SearchResponse response = SearchResponse.newBuilder().setIp(responseIP).setPort(responsePort).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        }
    }
}
