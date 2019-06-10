package server.requester;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import server.commons.chord.Chord;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import server.receptor.ServerThread;

public class GrpcCommunication {

    public static String ip;
    public static int port;

    public static void findNode(String ip, int port){
        ManagedChannel channel = CommunicationManager.initCommunication(ip, port);
        GreeterGrpc.GreeterStub output = GreeterGrpc.newStub(channel);

        StreamObserver<FindResponse> observer = new ServerThread.ObserverResponse(channel);

        output.findNode(FindMessage.newBuilder().setKey(Chord.getNode().getKey()).build(), observer);
    }

    public static void getRange(FindResponse findResponse) throws ServerException {
        ManagedChannel channel = CommunicationManager.initCommunication("localhost", findResponse.getPort());
        GreeterGrpc.GreeterStub output = GreeterGrpc.newStub(channel);

        output.getRange(
                GetRangeRequest.newBuilder().setNode(JsonUtils.serialize(Chord.getNode())).build(),
                new GetRangeObserver(ip, port));
    }
}
