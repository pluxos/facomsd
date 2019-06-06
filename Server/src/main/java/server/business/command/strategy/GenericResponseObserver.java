package server.business.command.strategy;

import io.grpc.GenericResponse;
import io.grpc.stub.StreamObserver;

public class GenericResponseObserver implements StreamObserver<GenericResponse> {

    private StreamObserver<GenericResponse> output;

    GenericResponseObserver(StreamObserver<GenericResponse> output) {
        this.output = output;
    }

    @Override
    public void onNext(GenericResponse genericResponse) {
        this.output.onNext(genericResponse);
        this.onCompleted();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }
}
