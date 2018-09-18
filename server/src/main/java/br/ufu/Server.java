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

import java.io.FileNotFoundException;

import static br.ufu.util.Constants.PROPERTY_LOG_PATH;
import static br.ufu.util.Constants.PROPERTY_SERVER_PORT;
import static br.ufu.util.UserParameters.get;
import static br.ufu.util.UserParameters.getInt;

public class Server {

    private final CrudRepository crudRepository;
    private final CrudService crudService;
    private final QueueService queueService;
    private final LogWriter logWriter;
    private final StartupRecoverService startupRecoverService;
    private final ServerListener serverListener;
    private final F1Listener f1Listener;
    private final F2Listener f2Listener;
    private final F3Listener f3Listener;

    public Server() throws FileNotFoundException {
        crudRepository = new CrudRepository();
        crudService = new CrudService(crudRepository);
        queueService = new QueueService();
        logWriter = new LogWriter(get(PROPERTY_LOG_PATH));
        startupRecoverService = new StartupRecoverService(crudService);
        serverListener = new ServerListener(queueService, getInt(PROPERTY_SERVER_PORT));
        f1Listener = new F1Listener(queueService);
        f2Listener = new F2Listener(queueService, logWriter);
        f3Listener = new F3Listener(queueService, crudService);
    }

    public CrudRepository getCrudRepository() {
        return crudRepository;
    }

    public CrudService getCrudService() {
        return crudService;
    }

    public QueueService getQueueService() {
        return queueService;
    }

    public LogWriter getLogWriter() {
        return logWriter;
    }

    public StartupRecoverService getStartupRecoverService() {
        return startupRecoverService;
    }

    public ServerListener getServerListener() {
        return serverListener;
    }

    public F1Listener getF1Listener() {
        return f1Listener;
    }

    public F2Listener getF2Listener() {
        return f2Listener;
    }

    public F3Listener getF3Listener() {
        return f3Listener;
    }

    private void startListeners() throws InterruptedException {

        Thread serverListenerThread = startThread(serverListener);
        Thread f1ListenerThread = startThread(f1Listener);
        Thread f2ListenerThread = startThread(f2Listener);
        Thread f3ListenerThread = startThread(f3Listener);

        serverListenerThread.join();
        f1ListenerThread.join();
        f2ListenerThread.join();
        f3ListenerThread.join();
    }

    public void start() throws InterruptedException {
        startupRecoverService.recover();
        startListeners();
    }

    private Thread startThread(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.start();
        return t;
    }
}
