package br.ufu.connection;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
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
    private final Integer port;

    public ClientConnection(String ip, Integer port) {
        this(ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build(), port);
    }

    ClientConnection(ManagedChannel channel, Integer port) {
        this.channel = channel;
        asyncStub = GreeterGrpc.newStub(channel);
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void send(String message, StreamObserver<Response> clientStream) {
        Request request = Request.newBuilder().setReq(message).build();
        asyncStub.message(request, new StreamObserver<Response>() {
            @Override
            public void onNext(Response resp) {
                log.info("Obtained response from monitoring: {}", resp.getResp());
                clientStream.onNext(resp);
                clientStream.onCompleted();
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                log.warn("Connection Failed: ", status.toString());
            }

            @Override
            public void onCompleted() {
                log.info("Completed with success!\n");
            }
        });

    }
}
