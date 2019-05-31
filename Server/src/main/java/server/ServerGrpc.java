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
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;

import java.util.HashMap;
import java.util.Map;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class ServerGrpc {
  private static final Logger logger = Logger.getLogger(ServerGrpc.class.getName());

  private Server server;
  public Map<BigInteger, byte[]> Database = new HashMap<BigInteger, byte[]>();

  private void start() throws IOException {
    /* The port on which the server should run */
    int port = 50051;
    server = ServerBuilder.forPort(port)
        .addService(new CrudImpl(this.Database))
        .build()
        .start();
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM shutdown hook.
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
   * Await termination on the main thread since the grpc library uses daemon threads.
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
    CrudImpl(Map<BigInteger, byte[]> pai)
    {
      this.Database = pai;
    }

    @Override
    public void create(CreateRequest req, StreamObserver<CreateResponse> responseObserver) {
      System.out.println("teste");
      byte[] chave = new byte[1];
      byte[] value = new byte[1];
      req.getKey().copyTo(chave, 0);
      req.getValue().copyTo(value, 0);

      System.out.println("CREATE: < " + new BigInteger(chave) + " , " + new String(value) + " >");
      CreateResponse response = CreateResponse.newBuilder().setRetorno(true).build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }

    @Override
    public void read(ReadRequest req, StreamObserver<ReadResponse> responseObserver) {
      String coco = "coco";
      byte[] value = coco.getBytes();
      ReadResponse response = ReadResponse.newBuilder().setValue(ByteString.copyFrom(value)).build();
      responseObserver.onNext(response);   
      responseObserver.onCompleted();
    }

    @Override
    public void update(UpdateRequest req, StreamObserver<UpdateResponse> responseObserver) {
      UpdateResponse response = UpdateResponse.newBuilder().setRetorno(true).build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }

    @Override
    public void delete(DeleteRequest req, StreamObserver<DeleteResponse> responseObserver) {
      DeleteResponse response = DeleteResponse.newBuilder().setRetorno(true).build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }

  }
}
