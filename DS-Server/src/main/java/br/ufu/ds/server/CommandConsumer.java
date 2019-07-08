package br.ufu.ds.server;

import br.ufu.ds.rpc.Request;
import br.ufu.ds.rpc.Response;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.math.BigInteger;

/**
 * @author Marcus
 */
public final class CommandConsumer extends Consumer {

    private final Database mDatabase = Database.getInstance();

    public CommandConsumer() {
        super(Queues.getInstance().getCommands());
    }

    @Override
    public void consume(Queues.Command command) {
        Request request = command.request;

        Response response = null;
        StreamObserver<Response> client = command.client;

        BigInteger key = null;
        ByteString data = null;

        String cmdDesc =  request.getRequestType().toString();

        switch (request.getRequestType().getNumber()) {
            case Request.RequestType.CREATE_VALUE:

                cmdDesc += String.format("(%d,%s)", request.getId(), request.getData().toStringUtf8());

                key = BigInteger.valueOf(request.getId());
                data = request.getData();

                try {
                    mDatabase.create(key, data);
                    response = Response.newBuilder()
                            .setSuccess(true)
                            .setDescription("Command executed: " + cmdDesc)
                            .setData(data)
                            .build();
                } catch (DatabaseException e) {
                    response = Response.newBuilder()
                            .setSuccess(false)
                            .setDescription(e.getMessage())
                            .build();
                }

                break;

            case Request.RequestType.DELETE_VALUE:

                key = BigInteger.valueOf(request.getId());

                data = mDatabase.delete(key);
                if (data != null) {
                    cmdDesc += String.format("(%d)", request.getId());
                    response = Response.newBuilder()
                            .setSuccess(true)
                            .setDescription("Command executed: " + cmdDesc)
                            .setData(data)
                            .build();
                } else {
                    response = Response.newBuilder()
                            .setSuccess(false)
                            .setDescription(String.format("Key %d not found", key))
                            .setData(null)
                            .build();
                }
                break;

            case Request.RequestType.READ_VALUE:
                key = BigInteger.valueOf(request.getId());

                data = mDatabase.read(key);
                if (data != null) {
                    cmdDesc += String.format("(%d)", request.getId());
                    response = Response.newBuilder()
                            .setSuccess(true)
                            .setDescription("Command executed: " + cmdDesc)
                            .setData(data)
                            .build();
                } else {
                    response = Response.newBuilder()
                            .setSuccess(false)
                            .setDescription(String.format("Key %d not found", key))
                            .build();
                }
                break;

            case Request.RequestType.UPDATE_VALUE:

                key = BigInteger.valueOf(request.getId());
                data = request.getData();

                try {
                    cmdDesc += String.format("(%d,%s)", request.getId(), request.getData().toStringUtf8());
                    mDatabase.update(key, data);
                    response = Response.newBuilder()
                            .setSuccess(true)
                            .setDescription("Command executed: " + cmdDesc)
                            .setData(data)
                            .build();
                } catch (DatabaseException e) {
                    response = Response.newBuilder()
                            .setSuccess(false)
                            .setDescription(e.getMessage())
                            .build();
                }

                break;
        }

        client.onNext(response);
        client.onCompleted();
    }
}
