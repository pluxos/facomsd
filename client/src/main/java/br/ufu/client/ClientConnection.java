package br.ufu.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import br.ufu.communication.GreeterGrpc;
import br.ufu.communication.Request;
import br.ufu.communication.Response;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class ClientConnection {

    private static final Logger log = LogManager.getLogger(ClientConnection.class);
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterStub asyncStub;

    public ClientConnection(String ip, int port) {
        this(ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build());
        System.out.println("Client connected to server " + ip + ":" + port);
    }

    ClientConnection(ManagedChannel channel) {
        this.channel = channel;
        asyncStub = GreeterGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void message(String message) {

        Request request = Request.newBuilder().setReq(message).build();
        try {
            asyncStub.message(request, new StreamObserver<Response>() {
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
