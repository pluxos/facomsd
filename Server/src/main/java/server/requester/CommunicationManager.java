package server.requester;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CommunicationManager {
	
    private static final String LOCALHOST = "localhost";

    public static ManagedChannel initCommunication(String ip, int port) {

        return ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
    }

    public static void shutdownCommunication(ManagedChannel channel) {
        if(channel != null)
            channel.shutdownNow();
    }
}
