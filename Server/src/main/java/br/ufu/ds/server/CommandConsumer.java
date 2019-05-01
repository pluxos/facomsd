package br.ufu.ds.server;

import br.ufu.ds.ServerProtocol;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.channels.SocketChannel;

/**
 * @author Marcus
 */
public class CommandConsumer extends Consumer {

    private final Server mServer;
    private final Database mDatabase = Database.getInstance();

    public CommandConsumer(Server server) {
        super(Queues.getInstance().getCommands());
        this.mServer = server;
    }

    @Override
    public void consume(Queues.Command command) {
        ServerProtocol.Request request = command.request;
        ServerProtocol.Response response = null;
        SocketChannel client = command.client;
        BigInteger key = null;
        ByteString data = null;

        String cmdDesc =  request.getRequestType().toString();

        switch (request.getRequestType().getNumber()) {
            case ServerProtocol.Request.RequestType.CREATE_VALUE:

                cmdDesc += String.format("(%d,%s)", request.getId(), request.getData().toStringUtf8());

                key = BigInteger.valueOf(request.getId());
                data = request.getData();

                try {
                    mDatabase.create(key, data);
                    response = ServerProtocol.Response.newBuilder()
                            .setSuccess(true)
                            .setDescription("Command executed: " + cmdDesc)
                            .setData(data)
                            .build();
                } catch (DatabaseException e) {
                    response = ServerProtocol.Response.newBuilder()
                            .setSuccess(false)
                            .setDescription(e.getMessage())
                            .build();
                }

                break;

            case ServerProtocol.Request.RequestType.DELETE_VALUE:

                key = BigInteger.valueOf(request.getId());

                data = mDatabase.delete(key);
                if (data != null) {
                    cmdDesc += String.format("(%d)", request.getId());
                    response = ServerProtocol.Response.newBuilder()
                            .setSuccess(true)
                            .setDescription("Command executed: " + cmdDesc)
                            .setData(data)
                            .build();
                } else {
                    response = ServerProtocol.Response.newBuilder()
                            .setSuccess(false)
                            .setDescription(String.format("Key %d not found", key))
                            .setData(null)
                            .build();
                }
                break;

            case ServerProtocol.Request.RequestType.READ_VALUE:
                key = BigInteger.valueOf(request.getId());

                data = mDatabase.read(key);
                if (data != null) {
                    cmdDesc += String.format("(%d)", request.getId());
                    response = ServerProtocol.Response.newBuilder()
                            .setSuccess(true)
                            .setDescription("Command executed: " + cmdDesc)
                            .setData(data)
                            .build();
                } else {
                    response = ServerProtocol.Response.newBuilder()
                            .setSuccess(false)
                            .setDescription(String.format("Key %d not found", key))
                            .build();
                }
                break;

            case ServerProtocol.Request.RequestType.UPDATE_VALUE:

                key = BigInteger.valueOf(request.getId());
                data = request.getData();

                try {
                    cmdDesc += String.format("(%d,%s)", request.getId(), request.getData().toStringUtf8());
                    mDatabase.update(key, data);
                    response = ServerProtocol.Response.newBuilder()
                            .setSuccess(true)
                            .setDescription("Command executed: " + cmdDesc)
                            .setData(data)
                            .build();
                } catch (DatabaseException e) {
                    response = ServerProtocol.Response.newBuilder()
                            .setSuccess(false)
                            .setDescription(e.getMessage())
                            .build();
                }

                break;
        }

        try {
            mServer.sendMessage(client, response);
        } catch (IOException e) {
            System.err.println("Can't send message to client: " + e.getMessage());
        }
    }
}
