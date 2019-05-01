package br.ufu.ds;

import br.ufu.ds.client.MenuListener;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;

/**
 * @author Marcus
 */
public class MenuImpl extends MenuListener {

    private Socket mSocket;

    public MenuImpl(Socket socket) {
        this.mSocket = socket;
    }

    protected void onCreateSelected(BigInteger key, ByteString value) {
        ServerProtocol.Request request =
                ServerProtocol.Request.newBuilder()
                                .setId(key.longValue())
                                .setData(value)
                                .setRequestType(ServerProtocol.Request.RequestType.CREATE)
                                .build();

        try {
            OutputStream out = mSocket.getOutputStream();
            out.write(request.toByteArray());
            out.flush();
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    protected void onReadSelected(BigInteger key) {
        ServerProtocol.Request request =
                ServerProtocol.Request.newBuilder()
                        .setId(key.longValue())
                        .setRequestType(ServerProtocol.Request.RequestType.READ)
                        .build();

        try {
            OutputStream out = mSocket.getOutputStream();
            out.write(request.toByteArray());
            out.flush();
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    protected void onUpdateSelected(BigInteger key, ByteString value) {
        ServerProtocol.Request request =
                ServerProtocol.Request.newBuilder()
                        .setId(key.longValue())
                        .setData(value)
                        .setRequestType(ServerProtocol.Request.RequestType.UPDATE)
                        .build();

        try {
            OutputStream out = mSocket.getOutputStream();
            out.write(request.toByteArray());
            out.flush();
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    protected void onDeleteSelected(BigInteger key) {
        ServerProtocol.Request request =
                ServerProtocol.Request.newBuilder()
                        .setId(key.longValue())
                        .setRequestType(ServerProtocol.Request.RequestType.DELETE)
                        .build();

        try {
            OutputStream out = mSocket.getOutputStream();
            out.write(request.toByteArray());
            out.flush();
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
