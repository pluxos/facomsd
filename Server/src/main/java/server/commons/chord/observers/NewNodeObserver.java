package server.commons.chord.observers;

import io.grpc.NewNodeResponse;
import io.grpc.stub.StreamObserver;
import server.commons.chord.Chord;
import server.commons.chord.FingerTable;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;

public class NewNodeObserver implements StreamObserver<NewNodeResponse> {

    public NewNodeObserver(){ }

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
    public void onCompleted() { }
}
