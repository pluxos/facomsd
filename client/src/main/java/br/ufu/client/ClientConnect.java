package br.ufu.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.servergreeting.GreeterGrpc;
import io.grpc.examples.servergreeting.Request;
import io.grpc.examples.servergreeting.Response;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class ClientConnect {

    private static final Logger log = LogManager.getLogger(ClientConnect.class);
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final GreeterGrpc.GreeterStub asyncStub;

    public ClientConnect(String ip, int port) {
        this(ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build());
    }

    ClientConnect(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = GreeterGrpc.newBlockingStub(channel);
        asyncStub = GreeterGrpc.newStub(channel);
    }

    public GreeterGrpc.GreeterBlockingStub getBlockingStub() {
        return blockingStub;
    }

    public GreeterGrpc.GreeterStub getAsyncStub() {
        return asyncStub;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String message, String individual) {
        String[] msg = message.split(" ");
        System.out.println("Message: " + message);
        System.out.println("Individual: " + individual);

        Request request = Request.newBuilder().setAll(message).build();
        try {
            asyncStub.say(request, new StreamObserver<Response>() {
                @Override
                public void onNext(Response note) {
                    System.out.println("Response: " + note.getResp());
                }

                @Override
                public void onError(Throwable t) {
                    Status status = Status.fromThrowable(t);
                    log.warn("Connection Failed: {}", status.getDescription());
                }

                @Override
                public void onCompleted() {
                    log.info("Completed with success!\n");
                }
            });
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            return;
        }

    }

}
