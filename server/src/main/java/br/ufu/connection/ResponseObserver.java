package br.ufu.connection;

import br.ufu.communication.Response;
import br.ufu.listener.F4Listener;
import br.ufu.model.Command;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.Logger;

public class ResponseObserver implements StreamObserver<Response> {

    private final Logger log;
    private final StreamObserver<Response> observer;
    private final F4Listener sourceServer;
    private final String server;
    private final Command item;

    public ResponseObserver(Logger log, StreamObserver<Response> observer, F4Listener sourceServer,
                            String server, Command item) {
        this.log = log;
        this.observer = observer;
        this.sourceServer = sourceServer;
        this.server = server;
        this.item = item;
    }

    @Override
    public void onNext(Response resp) {
        log.info("Obtained response from monitoring: {}", resp.getResp());
        observer.onNext(resp);
        observer.onCompleted();
    }

    @Override
    public void onError(Throwable t) {
        if (server.equals("RIGHT")) {
            sourceServer.changeRightServerAndSendAgain(item);
        } else {
            sourceServer.changeLeftServerAndSendAgain(item);
        }
        Status status = Status.fromThrowable(t);
        log.warn("Connection Failed: ", status.toString());
    }

    @Override
    public void onCompleted() {
        log.info("Completed with success!\n");
    }
}
