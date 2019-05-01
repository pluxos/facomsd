package br.ufu.ds;

import br.ufu.ds.server.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marcus
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
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

        Thread populateThread = new Thread(new DatabaseProducer());
        populateThread.start();
        populateThread.join();

        Server server = new Server(new InetSocketAddress(host, port));
        CommandConsumer cmdConsumer = new CommandConsumer(server);
        RequestConsumer reqConsumer = new RequestConsumer();
        LogConsumer logConsumer = new LogConsumer();

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(reqConsumer);
        executor.execute(cmdConsumer);
        executor.execute(logConsumer);
        executor.execute(() ->
            server.addListener(((client, message) ->
                Queues.getInstance().getRequests()
                        .add(new Queues.Command(client, message))
                )
            )
        );

        server.run();
        executor.shutdownNow();
    }
}
