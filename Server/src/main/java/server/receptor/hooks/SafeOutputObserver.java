package server.receptor.hooks;

import io.grpc.SafeOutputResponse;
import io.grpc.stub.StreamObserver;

public class SafeOutputObserver implements StreamObserver<SafeOutputResponse> {
    @Override
    public void onNext(SafeOutputResponse safeOutputResponse) {
        System.err.println(safeOutputResponse.getMessage());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }
}
