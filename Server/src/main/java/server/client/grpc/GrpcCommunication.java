package server.client.grpc;

import io.grpc.FindMessage;
import io.grpc.FindResponse;
import io.grpc.GetRangeRequest;
import io.grpc.GreeterGrpc;
import io.grpc.stub.StreamObserver;
import server.client.CommunicationManager;
import server.client.GetRangeObserver;
import server.commons.Chord.Chord;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import server.controller.ServerThread;

public class GrpcCommunication {

    public static String ip;
    public static int port;

    public static void findNode(String ip, int port){
        GreeterGrpc.GreeterStub output = CommunicationManager.initCommunication(ip, port);
        StreamObserver<FindResponse> observer = new ServerThread.ObserverResponse();

        output.findNode(FindMessage.newBuilder().setKey(Chord.getNode().getKey()).build(), observer);
    }

    public static void getRange(FindResponse findResponse) throws ServerException {
        GreeterGrpc.GreeterStub output = CommunicationManager.initCommunication(findResponse.getIp(), findResponse.getPort());

        System.out.println("MINHA PORTA: " + Chord.getNode().getPort());
        output.getRange(
                GetRangeRequest.newBuilder().setNode(JsonUtils.serialize(Chord.getNode())).build(),
                new GetRangeObserver(ip, port));
    }
}
