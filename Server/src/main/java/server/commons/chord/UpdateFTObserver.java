package server.commons.chord;

import io.grpc.ManagedChannel;
import io.grpc.UpdateFTResponse;
import io.grpc.stub.StreamObserver;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;

public class UpdateFTObserver implements StreamObserver<UpdateFTResponse> {

    private ManagedChannel channel;

    public UpdateFTObserver(ManagedChannel channel) {
        this.channel = channel;
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
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        this.channel.shutdownNow();
    }
}
