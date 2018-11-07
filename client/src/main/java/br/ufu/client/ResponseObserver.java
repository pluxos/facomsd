package br.ufu.client;

import br.ufu.communication.Response;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.Logger;

public class ResponseObserver implements StreamObserver<Response> {

    private final Logger log;

    public ResponseObserver(Logger log) {
        this.log = log;
    }

    @Override
    public void onNext(Response note) {
        log.info(note.getResp());
    }

    @Override
    public void onError(Throwable t) {
        Status status = Status.fromThrowable(t);
        log.warn("Connection Failed: {}", status.getDescription());
    }

    @Override
    public void onCompleted() {
        log.info("Completed with success!\n");
    }
}
