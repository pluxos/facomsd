package server.requester;

import io.grpc.*;
import server.commons.chord.Chord;
import server.commons.chord.observers.FindResponseObserver;
import server.commons.chord.observers.GetRangeObserver;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;

public class GrpcCommunication {

    public static String ip;
    public static int port;

    public static void findNode(String ip, int port){
        GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(ip, port);

        Context forked = Context.current().fork();
        Context old = forked.attach();

        try {
            stub.findNode(
                    FindMessage.newBuilder()
                            .setKey(Chord.getNode().getKey())
                            .build(),
                    new FindResponseObserver());
        } finally {
            forked.detach(old);
        }

    }

    public static void getRange(FindResponse findResponse) throws ServerException {
        GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication("localhost", findResponse.getPort());
        Context forked = Context.current().fork();
        Context old = forked.attach();

        try {
            stub.getRange(
                    GetRangeRequest.newBuilder().setNode(JsonUtils.serialize(Chord.getNode())).build(),
                    new GetRangeObserver(ip, port));
        } finally {
            forked.detach(old);
        }
    }
}
