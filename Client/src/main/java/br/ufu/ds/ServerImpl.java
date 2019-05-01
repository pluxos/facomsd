package br.ufu.ds;

import br.ufu.ds.client.ServerListener;

import java.net.Socket;

/**
 * @author Marcus
 */
public class ServerImpl extends ServerListener {

    public ServerImpl(Socket socket) {
        super(socket);
    }

    @Override
    protected void onMessage(ServerProtocol.Response response) {
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
    protected void onExit() {
        System.out.println("Closing server listener...");
    }

    @Override
    protected void onError(Exception ex) {
        System.err.println(ex.getMessage());
        System.exit(0);
    }
}
