package br.ufu.ds;

import br.ufu.ds.client.MenuListener;
import br.ufu.ds.rpc.Request;
import br.ufu.ds.rpc.Response;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.math.BigInteger;

public class MenuRpcImpl extends MenuListener {

    private ClientService clientService;

    public MenuRpcImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected void onCreateSelected(BigInteger key, ByteString value) {
        Request request = Request.newBuilder()
                .setData(value)
                .setRequestType(Request.RequestType.CREATE)
                .setId(key.longValue())
                .build();

        clientService.asyncStub.create(request, new ResponseHandler());
    }

    @Override
    protected void onReadSelected(BigInteger key) {
        Request request = Request.newBuilder()
                .setId(key.longValue())
                .setRequestType(Request.RequestType.READ)
                .build();

        clientService.asyncStub.read(request, new ResponseHandler());
    }

    @Override
    protected void onUpdateSelected(BigInteger key, ByteString value) {
        Request request = Request.newBuilder()
                .setData(value)
                .setRequestType(Request.RequestType.UPDATE)
                .setId(key.longValue())
                .build();

        clientService.asyncStub.update(request, new ResponseHandler());
    }

    @Override
    protected void onDeleteSelected(BigInteger key) {
        Request request = Request.newBuilder()
                .setRequestType(Request.RequestType.DELETE)
                .setId(key.longValue())
                .build();

        clientService.asyncStub.delete(request, new ResponseHandler());
    }

    @Override
    protected void onExit() {
        try {
            clientService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static class ResponseHandler implements StreamObserver<Response> {

        @Override
        public void onNext(Response response) {
            if (response.getSuccess()) {
                System.out.print("server say: " + response.getDescription());
                if (response.getData() != null) {
                    System.out.println(" data: " + response.getData().toStringUtf8());
                }
            } else {
                System.err.println("server say: " + response.getDescription());
            }
        }

        @Override
        public void onError(Throwable throwable) {
            System.err.println(throwable.getMessage());
        }

        @Override
        public void onCompleted() {

        }
    }
}
