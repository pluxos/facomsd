package br.ufu.ds;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

/**
 * @author Marcus
 */
public class Main {

    public static void main(String[] args) {
        Properties props = new Properties();
        int port;
        String host;

        File f = new File("server.config");
        InputStream in = null;
        try {
            in = f.exists() ? new FileInputStream(f) :
                    Main.class.getClassLoader().getResourceAsStream("server.config");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            props.load(in);
            host = props.getProperty("host.name");
            port = Integer.parseInt(props.getProperty("host.port"));
        } catch (NumberFormatException | IOException e) {
            port = 41234;
            host = "localhost";
        }

        Socket conn = null;

        try {
            conn = new Socket(host, port);
            conn.setKeepAlive(false);
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage() + ". Server: " + host + ":" + port);
            System.exit(0);
        }

        /* Implementação do Menu (loop infinito)  */
        MenuImpl menuListener = new MenuImpl(conn);
        ServerImpl serverListener = new ServerImpl(conn);

        // inicialização das threads
        Thread commandThread = new Thread(menuListener);
        Thread serverListenerThread = new Thread(serverListener);
        commandThread.setDaemon(true);
        serverListenerThread.setDaemon(true);

        commandThread.start();
        serverListenerThread.start();

        try {
            commandThread.join();
            if (conn.isClosed()) System.exit(0);
            serverListenerThread.join(5000);
            serverListener.dispose();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            serverListener.dispose();
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
