package br.ufu.ds.client;

import br.ufu.ds.IDisposable;
import br.ufu.ds.ServerProtocol;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author Marcus
 */
public abstract class ServerListener implements Runnable, IDisposable {

    private Socket mSocket;
    private boolean run = true;

    public ServerListener(Socket socket) {
        this.mSocket = socket;
    }

    @Override
    public final void run() {
        Thread.currentThread().setName("thread - ServerListener");
        byte[] buffer = new byte[4096];
        InputStream in = null;
        try {
            in = mSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            this.dispose();
        }

        while (run) {
            try {
                int nreads = in.read(buffer);
                if (nreads == -1) {
                    System.out.println("Disconnected from server!");
                    this.mSocket.close();
                    System.exit(0);
                    continue;
                } else if (nreads == 0) {
                    onMessage(ServerProtocol.Response.
                            newBuilder()
                                .setData(ByteString.copyFromUtf8("Server reply is null"))
                                .setSuccess(false)
                                .setDescription("Fail to receive server's response")
                            .build());
                    continue;
                }

                ByteBuffer bf = ByteBuffer.wrap(buffer, 0, nreads);
                ServerProtocol.Response response = null;
                try {
                    response = ServerProtocol.Response.parseFrom(bf);
                } catch (Exception ex) {
                    response = ServerProtocol.Response.
                            newBuilder()
                                .setData(null)
                                .setSuccess(false)
                                .setDescription(new String(buffer, 0, buffer.length))
                                .build();
                }

                onMessage(response);
            } catch (IOException e) {
                if (run) {
                    onError(new Exception("Connection close! Root cause: " + e.getMessage()));
                }
            }
        }

        onExit();
    }

    public final boolean isRunning() {
        return this.run;
    }

    public final void dispose() {
        this.run = false;
        try {
            this.mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void onMessage(ServerProtocol.Response response);
    protected abstract void onExit();
    protected abstract void onError(Exception ex);
}
