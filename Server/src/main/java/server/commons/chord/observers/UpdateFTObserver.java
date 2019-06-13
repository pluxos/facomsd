package server.commons.chord.observers;

import io.grpc.UpdateFTResponse;
import io.grpc.stub.StreamObserver;
import server.commons.chord.Chord;
import server.commons.chord.FingerTable;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;

public class UpdateFTObserver implements StreamObserver<UpdateFTResponse> {

    public UpdateFTObserver() {
    }

    @Override
    public void onNext(UpdateFTResponse updateFTResponse) {
        try {
            FingerTable ft = JsonUtils.deserialize(updateFTResponse.getFingerT(), FingerTable.class);

            Chord.getFt().updateFT(ft.getMap());
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) { }

    @Override
    public void onCompleted() { }
}
