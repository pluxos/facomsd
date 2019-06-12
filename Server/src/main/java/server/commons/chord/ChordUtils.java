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
    private static Node flagUpdate;
    private static Node flagNew;

    public static Integer successor(Integer p, Integer i) {
        return (int) (p + Math.pow(2, (i - 1)));
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

    public static void notifyNewNode(Node node) {
        Chord.getFt()
                .getMap()
                .forEach((key, value) -> {
                    if (flagNew != value) {
                        GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(value.getIp(), value.getPort());

                        Context forked = Context.current().fork();
                        Context old = forked.attach();

                        try {
                            System.out.println("New Node Request " +value.getIp() +":"+value.getPort());
                            stub.newNode(
                                    NewNodeRequest
                                            .newBuilder()
                                            .setNode(JsonUtils.serialize(Chord.getNode()))
                                            .setNewNode(JsonUtils.serialize(node))
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
