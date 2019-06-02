package server.client;

import io.grpc.GreeterGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CommunicationManager {
    private static ManagedChannel channel;

    public static GreeterGrpc.GreeterStub initCommunication(String ip, int port) {
        if (channel != null) {
            shutdownCommunication();
        }

        if(ip.equals("")) {
            ip = "localhost";
        }

        channel = ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
        return GreeterGrpc.newStub(channel);
    }

    private static void shutdownCommunication() {
        channel.shutdownNow();
    }
}
