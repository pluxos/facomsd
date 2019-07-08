package br.ufu.ds.grpc;


import br.ufu.ds.rpc.Request;
import br.ufu.ds.rpc.Response;
import br.ufu.ds.rpc.ServerProtocolGrpc;
import br.ufu.ds.server.Queues;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.BlockingQueue;

public final class ServerService extends ServerProtocolGrpc.ServerProtocolImplBase {

    private BlockingQueue<Queues.Command> mRequestQueue;

    protected ServerService() {
        mRequestQueue = Queues.getInstance().getRequests();
    }

    @Override
    public void create(Request request, StreamObserver<Response> responseObserver) {
        newCommand(request, responseObserver);
    }

    @Override
    public void read(Request request, StreamObserver<Response> responseObserver) {
        newCommand(request, responseObserver);
    }

    @Override
    public void update(Request request, StreamObserver<Response> responseObserver) {
        newCommand(request, responseObserver);
    }

    @Override
    public void delete(Request request, StreamObserver<Response> responseObserver) {
        newCommand(request, responseObserver);
    }

    public void newCommand(Request request, StreamObserver<Response> client) {
        try {
            mRequestQueue.put(new Queues.Command(client, request));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
