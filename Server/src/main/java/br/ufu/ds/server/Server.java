package br.ufu.ds.server;

import br.ufu.ds.ServerProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Marcus
 */
public final class Server implements Runnable {

    private final InetSocketAddress mAddress;
    private Selector mSelector;
    private ServerSocketChannel mServerSocket;
    private final ByteBuffer mSenderBuffer, mReaderBuffer;
    private List<Listener> mListeners;
    private boolean run = true;
    private Logger LOGGER = Logger.getLogger("Server");

    public Server(InetSocketAddress address) {
        this.mAddress = address;
        this.mSenderBuffer = ByteBuffer.allocate(4096);
        this.mReaderBuffer = ByteBuffer.allocate(4096);
        mListeners = new LinkedList<>();
    }

    public final void addListener(Listener listener) {
        synchronized (this) {
            mListeners.add(listener);
        }
    }

    public final boolean removeListener(Listener listener) {
        synchronized (this) {
            return mListeners.remove(listener);
        }
    }

    @Override
    public final void run() {
        try {
            start();
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
            System.exit(0);
        }
    }

    private void start() throws IOException {
        mSelector = Selector.open();
        mServerSocket = ServerSocketChannel.open();
        try {
            mServerSocket.bind(mAddress);
        } catch (Exception ex) {
            LOGGER.warning(String.format("Error to open server socket on %s:%d", mAddress.getHostName(), mAddress.getPort()));
            System.exit(0);
        }
        mServerSocket.configureBlocking(false);
        mServerSocket.register(mSelector, SelectionKey.OP_ACCEPT);

        LOGGER.info(String.format("Server listening on %s:%d", mAddress.getHostName(), mAddress.getPort()));

        while(run) {
            mSelector.select();

            Set<SelectionKey> selectedKeys = mSelector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {

                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    SocketChannel client = mServerSocket.accept();
                    registerToRead(client);
                }

                if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    messageReceived(client);
                }
                iter.remove();
            }
        }
    }

    private void messageReceived(SocketChannel client) throws IOException {
        int bytes = client.read(mReaderBuffer);

        // client closes connection
        if (bytes == -1) {
            LOGGER.info(client.getRemoteAddress().toString() + " disconnected!");
            client.close();
            return;
        }

        if (bytes == 0) {
            return; // server request is null
        }

        mReaderBuffer.flip();
        ServerProtocol.Request request = null;
        try {
            request = ServerProtocol.Request.parseFrom(mReaderBuffer);
        } catch (Exception ex) {
            sendString(client, "Hello, we can't process your message, sorry!\n");
        }

        LOGGER.info(client.getRemoteAddress().toString() + " sent: " + request.toString());

        if (request != null) {
            // notify observers
            for (Listener l : mListeners)
                l.onMessage(client, request);

        }
        mReaderBuffer.clear();
    }

    public final synchronized void sendString(SocketChannel client, String message) throws IOException{
        mSenderBuffer.put(message.getBytes(), 0, message.getBytes().length);
        mSenderBuffer.flip();

        if (client.write(mSenderBuffer) == 0) {
            System.out.println("0 bytes was write to " + client.getRemoteAddress().toString());
        }

        mSenderBuffer.clear();
    }

    public final synchronized void sendMessage(SocketChannel client, ServerProtocol.Response message)
            throws IOException {
        byte[] responseBytes = message.toByteArray();
        if (responseBytes.length == 0) {
            ServerProtocol.Response response = ServerProtocol.Response
                    .newBuilder()
                    .setData(null)
                    .setSuccess(false)
                    .setDescription("Server error: response message is null")
                    .build();
            responseBytes = response.toByteArray();
        }


        mSenderBuffer.put(responseBytes, 0, responseBytes.length);
        mSenderBuffer.flip();

        if (client.write(mSenderBuffer) == 0) {
            System.out.println("0 bytes was write to " + client.getRemoteAddress().toString());
        }

        mSenderBuffer.clear();
    }

    private void registerToRead(SocketChannel client) throws IOException {
        client.configureBlocking(false);
        client.register(mSelector, SelectionKey.OP_READ);

        LOGGER.info(client.getRemoteAddress().toString() + " connected!");
    }

    private void stop() {
        this.run = false;
        try {
            this.mSelector.close();
            this.mServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface Listener {

        void onMessage(SocketChannel client, ServerProtocol.Request message);

    }
}
