package br.ufu.ds;

import java.io.*;
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
            throw new RuntimeException(e);
        }

        try {
            props.load(in);
            host = props.getProperty("host.name");
            port = Integer.parseInt(props.getProperty("host.port"));
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        /* Implementação do Menu (loop infinito)  */
        MenuRpcImpl menuListener = new MenuRpcImpl(new ClientService(host, port));

        // inicialização das threads
        Thread commandThread = new Thread(menuListener);

        commandThread.setDaemon(true);

        commandThread.start();

        try {
            commandThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
