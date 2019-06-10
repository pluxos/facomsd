package server.requester;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.HashMap;
import java.util.Map;

public class CommunicationManager {

    private static Map<Integer, ManagedChannel> channelMap = new HashMap<>();

    public static ManagedChannel initCommunication(String ip, int port) {
        if(channelMap.get(port) == null)
            channelMap.put(port, ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build());

        return channelMap.get(port);
    }
}
