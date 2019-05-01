package br.ufu.ds.client;

import br.ufu.ds.ServerProtocol;
import com.google.protobuf.ByteString;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests server listener! An echo is sent of the data field for client.
 * @author Marcus
 */
public class ServerListenerTest {

    private ServerListener mServerListener;
    private Socket server;
    private Socket client;
    private ServerSocket mServer;

    private ServerProtocol.Response mServerResponse = null;
    private boolean mExited = false;
    private Exception mError = null;

    @Before
    public void setup() throws IOException {
        mServer = mock(ServerSocket.class);
        client = mock(Socket.class);
        server = mock(Socket.class);

        when(mServer.accept()).thenReturn(server);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);

        when(client.getInputStream()).thenReturn(in);
        when(server.getOutputStream()).thenReturn(out);

        mServerListener = new ServerListener(client) {

            @Override
            protected void onMessage(ServerProtocol.Response response) {
                mServerResponse = response;
            }

            @Override
            protected void onExit() {

            }

            @Override
            protected void onError(Exception ex) {

            }
        };
    }

    @Test
    public void onMessage() throws Exception {
        Thread t1 = new Thread(mServerListener);

        t1.start();

        String message = "Hello from server!";

        ServerProtocol.Response response =
                ServerProtocol.Response.newBuilder()
                    .setData(ByteString.copyFromUtf8(message))
                    .build();

        OutputStream out = server.getOutputStream();
        out.write(response.toByteArray());
        out.flush();

        t1.join(500);
        mServerListener.dispose();
        t1.join(500);

        assertNotNull(mServerResponse);
        assertEquals("Hello from server!", mServerResponse.getData().toStringUtf8());
    }

    @Test
    public void run() throws Exception {
        Thread t1 = new Thread(() -> mServerListener.run());

        t1.start();

        t1.join(500);

        assertTrue("is server listener running?", mServerListener.isRunning());

        mServerListener.dispose();

        t1.join(500);

        assertFalse("now, server isn't listener", mServerListener.isRunning());
    }

    @Test
    public void isRunning() throws Exception {
        Thread t1 = new Thread(() -> {
            mServerListener.run();
        });

        t1.start();

        t1.join(500);

        assertTrue("is server listener running?", mServerListener.isRunning());

        mServerListener.dispose();
    }

    @Test
    public void dispose() throws Exception {
        Thread t1 = new Thread(() -> {
            mServerListener.run();
        });

        t1.start();

        t1.join(500);

        mServerListener.dispose();

        assertFalse("now, server isn't listener", mServerListener.isRunning());
    }
}