package br.ufu.ds;

import br.ufu.ds.rpc.ServerProtocolGrpc;
import br.ufu.ds.rpc.ServerProtocolGrpc.ServerProtocolBlockingStub;
import br.ufu.ds.rpc.ServerProtocolGrpc.ServerProtocolStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public final class ClientService {

    private final ManagedChannel channel;
    public final ServerProtocolBlockingStub blockingStub;
    public final ServerProtocolStub asyncStub;

    public ClientService(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public ClientService(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = ServerProtocolGrpc.newBlockingStub(channel);
        asyncStub = ServerProtocolGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
