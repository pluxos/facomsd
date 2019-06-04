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
import com.google.protobuf.ByteString;
import server.ItemFila;
import singletons.F1;
import threads.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class ServerGrpc {
    private static final BlockingQueue<ItemFila> f1 = F1.getInstance();
    private static final Logger logger = Logger.getLogger(ServerGrpc.class.getName());

    private Server server;
    public Map<BigInteger, byte[]> Database = new HashMap<BigInteger, byte[]>();

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port).addService(new CrudImpl(this.Database)).build().start();
        logger.info("Server started, listening on " + port);
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

            System.out.println("CREATE: < " + new BigInteger(chave) + " , " + new String(value) + " >");
            itemfila.itemFilaCreate(responseObserver, chave, value);
            try {
                f1.put(itemfila);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        @Override
        public void read(ReadRequest req, StreamObserver<ReadResponse> responseObserver) {
            byte[] chave = new byte[req.getKeysize()];
            ItemFila itemfila = new ItemFila();
            req.getKey().copyTo(chave, 0);
            System.out.println("READ: < " + new BigInteger(chave) + " >");
            itemfila.itemFilaRead(responseObserver, chave);
            try {
                f1.put(itemfila);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        @Override
        public void update(UpdateRequest req, StreamObserver<UpdateResponse> responseObserver) {
            byte[] chave = new byte[req.getKeysize()];
            ItemFila itemfila = new ItemFila();
            byte[] value = new byte[req.getValuesize()];
            req.getKey().copyTo(chave, 0);
            req.getValue().copyTo(value, 0);

            System.out.println("UPDATE: < " + new BigInteger(chave) + " , " + new String(value) + " >");
            itemfila.itemFilaUpdate(responseObserver, chave, value);
            try {
                f1.put(itemfila);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        @Override
        public void delete(DeleteRequest req, StreamObserver<DeleteResponse> responseObserver) {
            byte[] chave = new byte[req.getKeysize()];
            ItemFila itemfila = new ItemFila();
            req.getKey().copyTo(chave, 0);
            System.out.println("DELETE: < " + new BigInteger(chave) + " >");
            itemfila.itemFilaDelete(responseObserver, chave);
            try {
                f1.put(itemfila);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

    }
}
