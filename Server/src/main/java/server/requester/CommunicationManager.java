package server.requester;

import io.grpc.GreeterGrpc;
import io.grpc.ManagedChannelBuilder;

import java.util.HashMap;
import java.util.Map;

public class CommunicationManager {

    private static Map<Integer, GreeterGrpc.GreeterStub> channelMap = new HashMap<>();

    public static GreeterGrpc.GreeterStub initCommunication(String ip, int port) {
        if(channelMap.get(port) == null)
            channelMap.put(
                    port,
                    GreeterGrpc.newStub(ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build())
            );

        return channelMap.get(port);
    }
}
