package br.ufu.connection;

import br.ufu.communication.Response;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.Logger;

public class ResponseObserver implements StreamObserver<Response> {

    private final Logger log;
    private final StreamObserver<Response> observer;

    public ResponseObserver(Logger log, StreamObserver<Response> observer) {
        this.log = log;
        this.observer = observer;
    }

    @Override
    public void onNext(Response resp) {
        log.info("Obtained response from monitoring: {}", resp.getResp());
        observer.onNext(resp);
        observer.onCompleted();
    }

    @Override
    public void onError(Throwable t) {
        Status status = Status.fromThrowable(t);
        log.warn("Connection Failed: ", status.toString());
    }

    @Override
    public void onCompleted() {
        log.info("Completed with success!\n");
    }
}
