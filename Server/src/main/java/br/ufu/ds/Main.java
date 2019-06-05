package br.ufu.ds;

import br.ufu.ds.grpc.ServerRpc;
import br.ufu.ds.server.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marcus
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Config config = Config.getInstance();

        CommandConsumer cmdConsumer = new CommandConsumer();
        RequestConsumer reqConsumer = new RequestConsumer();
        LogConsumer logConsumer = new LogConsumer();
        SnapshotLog snap = SnapshotLog.getInstance();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(snap);
        executor.execute(reqConsumer);
        executor.execute(cmdConsumer);
        executor.execute(logConsumer);

        Thread serverThread = new Thread(new ServerRpc(config.getPort()));
        serverThread.start();
        serverThread.join();

        executor.shutdownNow();
    }
}
