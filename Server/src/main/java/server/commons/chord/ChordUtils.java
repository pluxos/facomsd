package server.commons.chord;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
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
                ManagedChannel channel = CommunicationManager.initCommunication(value.getIp(), value.getPort());
                GreeterGrpc.GreeterStub stub = GreeterGrpc.newStub(channel);

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
                        ManagedChannel channel = CommunicationManager.initCommunication(value.getIp(), value.getPort());
                        GreeterGrpc.GreeterStub stub = GreeterGrpc.newStub(channel);

                        try {
                            System.out.println("New Node Request " +value.getIp() +":"+value.getPort());
                            stub.newNode(
                                    NewNodeRequest
                                            .newBuilder()
                                            .setNode(JsonUtils.serialize(Chord.getNode()))
                                            .setNewNode(JsonUtils.serialize(node))
                                            .build(),
                                    new StreamObserver<NewNodeResponse>() {
                                        @Override
                                        public void onNext(NewNodeResponse newNodeResponse) {
                                            System.out.println("New Node Response");
                                            if (newNodeResponse.getUpdate()) {
                                                try {
                                                    FingerTable ft = JsonUtils.deserialize(
                                                            newNodeResponse.getFingerT(),
                                                            FingerTable.class
                                                    );

                                                    Chord.getFt().updateFT(ft.getMap());

                                                } catch (ServerException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable throwable) {

                                        }

                                        @Override
                                        public void onCompleted() {
                                        }
                                    }
                            );
                            flagNew = value;
                        } catch (ServerException e) {
                            e.printStackTrace();
                        }
                    }
                });
        flagNew = null;
    }
}
