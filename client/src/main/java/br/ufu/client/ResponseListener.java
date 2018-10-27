package br.ufu.client;

import io.grpc.Status;
import io.grpc.examples.servergreeting.GreeterGrpc;
import io.grpc.examples.servergreeting.Request;
import io.grpc.examples.servergreeting.Response;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseListener implements Runnable {

    private static final Logger log = LogManager.getLogger(ResponseListener.class);
    private GreeterGrpc.GreeterStub asyncStub;
    private String individual;

    public ResponseListener(GreeterGrpc.GreeterStub asyncStub, String i) {
        this.asyncStub = asyncStub;
        this.individual = i;
    }

    @Override
    public void run() {
        Request request = Request.newBuilder().setAll(this.individual).build();
        asyncStub.notify(request, new StreamObserver<Response>() {
            @Override
            public void onNext(Response note) {
                System.out.println(note.getResp());
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                log.warn("RouteChat Failed: {0}", status);
            }

            @Override
            public void onCompleted() {
                log.info("Completed with success!\n");
            }
        });
    }
}
