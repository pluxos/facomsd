package server.commons.chord;

import io.grpc.UpdateFTResponse;
import io.grpc.stub.StreamObserver;

public class UpdateFTObserver implements StreamObserver<UpdateFTResponse> {
    @Override
    public void onNext(UpdateFTResponse updateFTResponse) {
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }
}
