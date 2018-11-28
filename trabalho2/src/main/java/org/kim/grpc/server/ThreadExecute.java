package org.kim.grpc.server;

import io.grpc.stub.StreamObserver;
import org.kim.grpc.ServerResponse;

import java.util.concurrent.BlockingQueue;

public class ThreadExecute extends Thread {

    private static ThreadExecute threadExecute;
    private BlockingQueue<Command> F3;
    private DataStorage dataStorage;

    public ThreadExecute(BlockingQueue<Command> f3, DataStorage dataStorage) {
        F3 = f3;
        this.dataStorage = dataStorage;
    }

    public ThreadExecute() {}

    public static ThreadExecute init() {
        if (threadExecute == null) {
            threadExecute = new ThreadExecute();
            threadExecute.start();
        }

        return threadExecute;
    }

    @Override
    public void run() {
        System.out.println("Thread Execute started");
        while (true) {
            ServerResponse response;
            try {
                System.out.println("Starting resolving commands...");
                Command commands = F3.take();
                StreamObserver<ServerResponse> responseObserver = commands.getObserver();

                switch (commands.getType()) {
                    case "CREATE":
                        System.out.println("Resolving request CREATE received from client");

                        if (dataStorage.getData().containsKey(commands.getKey())) {
                            response = ServerResponse.newBuilder()
                                    .setResponse("Key already exists!")
                                    .build();
                        }

                        else {
                            dataStorage.setData(commands.getKey(), commands.getData());

                            response = ServerResponse.newBuilder()
                                    .setResponse("DataStorage saved! Key: " + commands.getKey())
                                    .build();
                        }

                        responseObserver.onNext(response);
                        responseObserver.onCompleted();

                        System.out.println("Commands resolved");

                        break;

                    case "READ":
                        System.out.println("Resolving request READ received from client");

                        if (dataStorage.getData().containsKey(commands.getKey())) {
                            response = ServerResponse.newBuilder()
                                    .setResponse(dataStorage.getData().get(commands.getKey()))
                                    .build();
                        }

                        else {
                            response = ServerResponse.newBuilder()
                                    .setResponse("Key not found!")
                                    .build();
                        }

                        responseObserver.onNext(response);
                        responseObserver.onCompleted();

                        break;

                    case "UPDATE":
                        System.out.println("Resolving request UPDATE received from client");

                        if (dataStorage.getData().containsKey(commands.getKey())) {
                            dataStorage.setData(commands.getKey(), commands.getData());

                            response = ServerResponse.newBuilder()
                                    .setResponse("DataStorage updated!")
                                    .build();
                        }

                        else {
                            response = ServerResponse.newBuilder()
                                    .setResponse("Key not found!")
                                    .build();
                        }

                        responseObserver.onNext(response);
                        responseObserver.onCompleted();

                        break;

                    case "DELETE":
                        System.out.println("Resolving request DELETE received from client");

                        if (dataStorage.getData().containsKey(commands.getKey())) {
                            dataStorage.removeData(commands.getKey());
                            response = ServerResponse.newBuilder()
                                    .setResponse("DataStorage deleted!")
                                    .build();
                        }

                        else {
                            response = ServerResponse.newBuilder()
                                    .setResponse("Key not found!")
                                    .build();
                        }

                        responseObserver.onNext(response);
                        responseObserver.onCompleted();

                        break;
                }
            }
            catch (InterruptedException e) { System.out.println("Thread Execute Error: " + e.getMessage()); }
        }
    }
}
