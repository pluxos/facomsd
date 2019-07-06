package server.commons.chord;

import io.grpc.Context;
import io.grpc.GreeterGrpc;
import io.grpc.NewNodeRequest;
import io.grpc.UpdateFTRequest;
import server.commons.chord.observers.NewNodeObserver;
import server.commons.chord.observers.UpdateFTObserver;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import server.requester.CommunicationManager;

public class ChordUtils {
    private static ChodNode flagUpdate;
    private static ChodNode flagNew;

    public static Integer successor(Integer keyNode, Integer i) {
        return (int) (keyNode + Math.pow(2, (i - 1)));
    }

    public static void notifyUpdateFT() {

        Chord.getFt().getMap().forEach((key, value) -> {
            if(flagUpdate != value){
                GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(value.getIp(), value.getPort());

                Context forked = Context.current().fork();
                Context old = forked.attach();

                try {
                    stub.updateFT(
                            UpdateFTRequest
                                    .newBuilder()
                                    .setFingerT(JsonUtils.serialize(Chord.getFt().getMap()))
                                    .setNode(JsonUtils.serialize(Chord.getChodNode()))
                                    .build(),
                            new UpdateFTObserver()
                    );
                    flagUpdate = value;
                } catch (ServerException e) {
                    e.printStackTrace();
                } finally {
                    forked.detach(old);
                }
            }
        });
        flagUpdate = null;
    }

    public static void notifyNewNode(ChodNode chodNode) {
        Chord.getFt()
                .getMap()
                .forEach((key, value) -> {
                    if (flagNew != value) {
                        GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(value.getIp(), value.getPort());

                        Context forked = Context.current().fork();
                        Context old = forked.attach();

                        try {
                            stub.newNode(
                                    NewNodeRequest
                                            .newBuilder()
                                            .setNode(JsonUtils.serialize(Chord.getChodNode()))
                                            .setNewNode(JsonUtils.serialize(chodNode))
                                            .build(),
                                    new NewNodeObserver()
                            );
                            flagNew = value;
                        } catch (ServerException e) {
                            e.printStackTrace();
                        } finally {
                            forked.detach(old);
                        }
                    }
                });
        flagNew = null;
    }
}
