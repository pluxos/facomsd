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

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple HelloWorldClient that requests a greeting from the {@link HelloWorldServer}.
 */
public class ClientGrpc {
  private static final Logger logger = Logger.getLogger(ClientGrpc.class.getName());

  private final ManagedChannel channel;
  private final CrudGrpc.CrudBlockingStub blockingStub;

  /** Construct ClientGrpc connecting to HelloWorld server at {@code host:port}. */
  public ClientGrpc(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build());
  }

  /** Construct ClientGrpc for accessing HelloWorld server using the existing channel. */
  ClientGrpc(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = CrudGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }


   public void create(int key, String value) {
    logger.info("Tentando enviar " + key + " ...");
    CreateRequest request = CreateRequest.newBuilder()
                                         .setKey(key)
                                         .setValue(value).build();
    CreateResponse response;
    try {
      response = blockingStub.create(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }
    if (response.getRetorno() == true) {
      logger.info("Dado inserido com sucesso!");
    } else {
      logger.info("A chave especificada já existe no banco de dados!");
    }
   }

   public void read(int key) {
    logger.info("Procurando o elemento de chave: " + key + " ...");
    ReadRequest request = ReadRequest.newBuilder().setKey(key).build();
    ReadResponse response;
    try {
      response = blockingStub.read(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
    }
    if(response.getValue() != null) {
      logger.info("Recebido o valor: " + response.getValue());
    } else {
      logger.info("Não existe a chave especificada!");
    }
   }

   public void update(int key, String value) {
     logger.info("Mudando elemento de chave: " + key + " para valor: " + value + " ...");
     UpdateRequest request = UpdateRequest.newBuilder().setKey(key)
                                                       .setValue(value)
                                                       .build();
     UpdateResponse response;
     try {
      response = blockingStub.update(request);
     } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
     }
     if (response.getRetorno() == true) {
      logger.info("O elemento foi alterado com sucesso!");
     } else {
       logger.info("Chave inexistente");
     }
   }

   public void delete(int key) {
     logger.info("Removendo elemento de chave: " + key + " ...");
     DeleteRequest request = DeleteRequest.newBuilder().setKey(key).build();
     DeleteResponse response;
     try {
       response = blockingStub.delete(request);
     } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Rpc failed: {0}", e.getStatus());
      return;
     }
     if (response.getRetorno() == true) {
       logger.info("O elemento foi removido com sucesso!");
     } else {
       logger.info("Não existe a chave especificada!");
     }
   }
  /**
   * Greet server. If provided, the first element of {@code args} is the name to use in the
   * greeting.
   */
  public static void main(String[] args) throws Exception {
    ClientGrpc client = new ClientGrpc("localhost", 50051);
    try {
      client.create(1,"teste");
      client.create(2, "coco");
      client.read(1);
      client.update(1, "coco fedido");
      client.read(1);
      client.delete(1);
      client.read(1);
    } finally {
      client.shutdown();
    }
  }
}
