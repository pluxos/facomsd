package br.ufu;

import br.ufu.connections.ServerConnect;
import br.ufu.listener.*;
import br.ufu.repository.CrudRepository;
import br.ufu.service.CrudService;
import br.ufu.service.QueueService;
import br.ufu.service.StartupRecoverService;
import br.ufu.util.UserParameters;

import java.io.IOException;
import java.math.BigInteger;

import static br.ufu.util.Constants.*;

public class Server {

    private CrudRepository crudRepository;
    private CrudService crudService;
    private QueueService queueService;
    private StartupRecoverService startupRecoverService;
    private F1Listener f1Listener;
    private F2Listener f2Listener;
    private F3Listener f3Listener;
    private UserParameters userParameters;
    private SnapshotSchedule snapshotSchedule;
    private ServerConnect serverConnect;

    public Server(String[] args) {
        userParameters = new UserParameters(args);
    }

    private void init() throws IOException {
        crudRepository = new CrudRepository();
        queueService = new QueueService();
        crudService = new CrudService(getCrudRepository());
        startupRecoverService = new StartupRecoverService(getCrudService(), userParameters, new BigInteger("32423482304324"));
        serverConnect = new ServerConnect(getQueueService(), getUserParameters().getInt(PROPERTY_SERVER_PORT));
        f1Listener = new F1Listener(getQueueService());
        f2Listener = new F2Listener(getQueueService(), getUserParameters().get(PROPERTY_LOG_PATH), new BigInteger("32423482304324"));
        f3Listener = new F3Listener(getQueueService(), getCrudService());
        snapshotSchedule = new SnapshotSchedule(getCrudRepository(), getF2Listener(),
                getUserParameters().getInt(PROPERTY_SNAP_TIME), getUserParameters().get(PROPERTY_SNAP_PATH), new BigInteger("32423482304324"));
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

    public StartupRecoverService getStartupRecoverService() {
        return startupRecoverService;
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

    public UserParameters getUserParameters() {
        return userParameters;
    }

    public SnapshotSchedule getSnapshotSchedule() { return snapshotSchedule; }

    public ServerConnect getServerConnect() {
        return serverConnect;
    }

    private void startListeners() throws InterruptedException {

        Thread f1ListenerThread = startThread(getF1Listener());
        Thread f2ListenerThread = startThread(getF2Listener());
        Thread f3ListenerThread = startThread(getF3Listener());
        Thread snapshotSchedule = startThread(getSnapshotSchedule());

        f1ListenerThread.join();
        f2ListenerThread.join();
        f3ListenerThread.join();
        snapshotSchedule.join();
        serverConnect.blockUntilShutdown();
    }

    public void start() throws InterruptedException, IOException {
        init();
        getStartupRecoverService().recover();
        serverConnect.start();
        startListeners();
    }

    private Thread startThread(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.start();
        return t;
    }
}
