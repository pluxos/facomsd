package server.commons.Chord;

import io.grpc.GreeterGrpc;
import io.grpc.UpdateFTRequest;
import server.client.CommunicationManager;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;

import java.util.Map;

public class ChordUtils {
    public static Integer sucessor(Integer p, Integer i) {
        return (int) (p + Math.pow(2, (i - 1)));
    }

    public static void notifyUpdateFT(Map<Integer, Node> ft) {
        ft.forEach((key, value) -> {
            GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(value.getIp(), value.getPort());

            try {
                stub.updateFT(
                        UpdateFTRequest
                                .newBuilder()
                                .setFingerT(JsonUtils.serialize(ft))
                                .build(),
                        new UpdateFTObserver()
                );
            } catch (ServerException e) {
                e.printStackTrace();
            }
        });
    }
}
