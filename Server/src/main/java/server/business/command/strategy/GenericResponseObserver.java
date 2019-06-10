package server.business.command.strategy;

import io.grpc.GenericResponse;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

public class GenericResponseObserver implements StreamObserver<GenericResponse> {

    private StreamObserver<GenericResponse> output;
    private ManagedChannel channel;

    GenericResponseObserver(StreamObserver<GenericResponse> output, ManagedChannel channel) {
        this.output = output;
        this.channel = channel;
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
        this.channel.shutdownNow();
    }
}
