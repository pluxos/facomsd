package br.ufu.connection;

import br.ufu.communication.RequestKeyValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import br.ufu.communication.GreeterGrpc;
import br.ufu.communication.Request;
import br.ufu.communication.Response;
import io.grpc.StatusRuntimeException;
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

    private ClientConnection(ManagedChannel channel, Integer port) {
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

    public void send(String message, StreamObserver<Response> observer) {
        String[] msg = message.split(" ");

        switch (msg[0]) {
            case "CREATE":
                sendCreate(msg[1], msg[2], observer);
                break;
            case "READ":
                sendRead(msg[1], observer);
                break;
            case "UPDATE":
                sendUpdate(msg[1], msg[2], observer);
                break;
            case "DELETE":
                sendDelete(msg[1], observer);
                break;
            default:
                log.error("Invalid CRUD operation!");
        }
    }

    private void sendCreate(String key, String value, StreamObserver<Response> observer) {
        RequestKeyValue request = RequestKeyValue.newBuilder().setKey(key).setValue(value).build();
        try {
            asyncStub.create(request, new ResponseObserver(log, observer));
        } catch (StatusRuntimeException e) {
            log.error("Create RPC failed: {}", e.getStatus());
        }
    }

    private void sendRead(String key, StreamObserver<Response> observer) {
        Request request = Request.newBuilder().setKey(key).build();
        try {
            asyncStub.read(request, new ResponseObserver(log, observer));
        } catch (StatusRuntimeException e) {
            log.error("Read RPC failed: {}", e.getStatus());
        }
    }

    private void sendUpdate(String key, String value, StreamObserver<Response> observer) {
        RequestKeyValue request = RequestKeyValue.newBuilder().setKey(key).setValue(value).build();
        try {
            asyncStub.update(request, new ResponseObserver(log, observer));
        } catch (StatusRuntimeException e) {
            log.error("Update RPC failed: {}", e.getStatus());
        }
    }

    private void sendDelete(String key, StreamObserver<Response> observer) {
        Request request = Request.newBuilder().setKey(key).build();
        try {
            asyncStub.delete(request, new ResponseObserver(log, observer));
        } catch (StatusRuntimeException e) {
            log.error("Delete RPC failed: {}", e.getStatus());
        }
    }
}
