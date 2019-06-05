package serverclient.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import serverclient.server.services.impl.MessageServiceProtImpl;

import java.util.Arrays;
import java.util.logging.Logger;

public class AppServer {

    private static final Logger logger = Logger.getLogger(AppServer.class.getName());

    private static int initialPort = 42420;
    private Server server;

    private void start() throws Exception {
        logger.info("Starting the grpc server");

        server = ServerBuilder.forPort(initialPort)
                .addService(new MessageServiceProtImpl())
                .build()
                .start();

        logger.info("Server started. Listening on port " + initialPort);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** JVM is shutting down. Turning off grpc server as well ***");
            AppServer.this.stop();
            System.err.println("*** shutdown complete ***");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }


    public static void main(String[] args) throws Exception {
        logger.info("Server startup. Args = " + Arrays.toString(args));
        final AppServer server = new AppServer();

        server.start();
        server.blockUntilShutdown();
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public int getLastPort() {
        return initialPort;
    }

    public void increasePort() {
        initialPort++;
    }

//    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {
//
//        @Override
//        public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
//            HelloResponse response = HelloResponse.newBuilder().setMessage("Hello " + request.getName()).build();
//            responseObserver.onNext(response);
//            responseObserver.onCompleted();
//        }
//    }
}
