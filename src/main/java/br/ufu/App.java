package br.ufu;

import br.ufu.listener.F1Listener;
import br.ufu.listener.F2Listener;
import br.ufu.listener.F3Listener;
import br.ufu.listener.ServerListener;
import br.ufu.repository.CrudRepository;
import br.ufu.service.CrudService;
import br.ufu.service.QueueService;
import br.ufu.service.StartupRecoverService;
import br.ufu.writer.LogWriter;

import java.io.IOException;

import static br.ufu.util.Constants.PROPERTY_LOG_PATH;
import static br.ufu.util.Constants.PROPERTY_SERVER_PORT;
import static br.ufu.util.UserParameters.get;
import static br.ufu.util.UserParameters.getInt;

public class App {


    public static void main(String[] args) throws IOException, InterruptedException {
        run();
    }

    private static void run() throws IOException, InterruptedException {

        CrudRepository crudRepository = new CrudRepository();
        CrudService crudService = new CrudService(crudRepository);
        QueueService queueService = new QueueService();

        StartupRecoverService startupRecoverService = new StartupRecoverService(crudService);
        startupRecoverService.recover();

        LogWriter logWriter = new LogWriter(get(PROPERTY_LOG_PATH));

        startListeners(crudService, queueService, logWriter);
    }

    private static void startListeners(CrudService crudService, QueueService queueService, LogWriter logWriter) throws InterruptedException {
        F1Listener f1Listener = new F1Listener(queueService);
        F2Listener f2Listener = new F2Listener(queueService, logWriter);
        F3Listener f3Listener = new F3Listener(queueService, crudService);

        Thread t = new Thread(new ServerListener(queueService, getInt(PROPERTY_SERVER_PORT)));
        t.start();

        Thread tf1 = new Thread(f1Listener);
        Thread tf2 = new Thread(f2Listener);
        Thread tf3 = new Thread(f3Listener);

        tf1.start();
        tf2.start();
        tf3.start();

        t.join();
    }
}
