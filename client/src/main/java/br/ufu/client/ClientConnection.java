package br.ufu.client;

import br.ufu.communication.RequestKeyValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import br.ufu.communication.GreeterGrpc;
import br.ufu.communication.Request;
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

    private ClientConnection(ManagedChannel channel) {
        this.channel = channel;
        asyncStub = GreeterGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    public void message(String message) {
        String[] msg = message.split(" ");

        switch (msg[0]) {
            case "CREATE":
                sendCreate(msg[1], msg[2]);
                break;
            case "READ":
                sendRead(msg[1]);
                break;
            case "UPDATE":
                sendUpdate(msg[1], msg[2]);
                break;
            case "DELETE":
                sendDelete(msg[1]);
                break;
            default:
                log.warn("Invalid CRUD operation!");
        }
    }

    private void sendCreate(String key, String value) {
        RequestKeyValue request = RequestKeyValue.newBuilder().setKey(key).setValue(value).build();
        try {
            asyncStub.create(request, new ResponseObserver(log));
        } catch (StatusRuntimeException e) {
            log.error("Create RPC failed: {}", e.getStatus());
        }
    }

    private void sendRead(String key) {
        Request request = Request.newBuilder().setKey(key).build();
        try {
            asyncStub.read(request, new ResponseObserver(log));
        } catch (StatusRuntimeException e) {
            log.error("Read RPC failed: {}", e.getStatus());
        }
    }

    private void sendUpdate(String key, String value) {
        RequestKeyValue request = RequestKeyValue.newBuilder().setKey(key).setValue(value).build();
        try {
            asyncStub.update(request, new ResponseObserver(log));
        } catch (StatusRuntimeException e) {
            log.error("Update RPC failed: {}", e.getStatus());
        }
    }

    private void sendDelete(String key) {
        Request request = Request.newBuilder().setKey(key).build();
        try {
            asyncStub.delete(request, new ResponseObserver(log));
        } catch (StatusRuntimeException e) {
            log.error("Delete RPC failed: {}", e.getStatus());
        }
    }

}
