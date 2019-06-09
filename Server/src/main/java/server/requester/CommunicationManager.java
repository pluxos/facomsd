package server.requester;

import io.grpc.GreeterGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CommunicationManager {
	
    private static ManagedChannel channel;
    private static String stateIp;
    private static int statePort;
    private static final String LOCALHOST = "localhost";

    public static GreeterGrpc.GreeterStub initCommunication(String ip, int port) {
        if(ip == null || ip.equals("")) {
            ip = LOCALHOST;
        }

        if(ip.equals(stateIp) && port == statePort) {
            return GreeterGrpc.newStub(channel);
        }

        shutdownCommunication();
        stateIp = ip;
        statePort = port;

        channel = ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
        return GreeterGrpc.newStub(channel);
    }

    private static void shutdownCommunication() {
        if(channel != null)
            channel.shutdownNow();
    }
}
