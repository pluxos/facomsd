package br.ufu;

import br.ufu.connection.AtomixConnection;
import br.ufu.connection.ServerConnection;
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
    private F4Listener f4Listener;
    private UserParameters userParameters;
    private SnapshotSchedule snapshotSchedule;
    private ServerConnection serverConnect;
    private AtomixConnection atomixConnection;

    public Server(String[] args) {
        userParameters = new UserParameters(args);
    }

    private void init() throws IOException {
        BigInteger serverId = new BigInteger(userParameters.get(PROPERTY_SERVER_ID));
        BigInteger smallerKey = new BigInteger(userParameters.get(PROPERTY_SMALLER_KEY));
        BigInteger maxKey = new BigInteger(userParameters.get(PROPERTY_MAX_KEY));
        Integer serverPort = userParameters.getInt(PROPERTY_SERVER_PORT);
        Integer serverAtomixPort = userParameters.getInt(PROPERTY_SERVER_ATOMIX_PORT);
        String logPath = userParameters.get(PROPERTY_LOG_PATH);
        String snapPath = userParameters.get(PROPERTY_SNAP_PATH);
        Integer snapTime = userParameters.getInt(PROPERTY_SNAP_TIME);
        Integer leftServer = userParameters.getInt(PROPERTY_LEFT_SERVER);
        Integer rightServer = userParameters.getInt(PROPERTY_RIGHT_SERVER);
        printServerInfo(serverPort, serverId, smallerKey, leftServer, rightServer);
        int[] clusterAddresses = new int[]{5446, 5447};

        crudRepository = new CrudRepository();
        queueService = new QueueService();
        crudService = new CrudService(getCrudRepository());
        startupRecoverService = new StartupRecoverService(getCrudService(), userParameters, serverId);
        serverConnect = new ServerConnection(getQueueService(), serverPort, maxKey);
        f1Listener = new F1Listener(getQueueService(), serverId, smallerKey);
        f2Listener = new F2Listener(getQueueService(), logPath , serverId);
        f3Listener = new F3Listener(getQueueService(), getCrudService());
        f4Listener = new F4Listener(getQueueService(), leftServer, rightServer, serverId, maxKey);
        snapshotSchedule = new SnapshotSchedule(getCrudRepository(), getF2Listener(), snapTime, snapPath, serverId);
        atomixConnection = new AtomixConnection(serverAtomixPort, clusterAddresses);
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

    public F4Listener getF4Listener() {
        return f4Listener;
    }

    public UserParameters getUserParameters() {
        return userParameters;
    }

    public SnapshotSchedule getSnapshotSchedule() {
        return snapshotSchedule;
    }

    public ServerConnection getServerConnect() {
        return serverConnect;
    }

    private void startListeners() throws InterruptedException {

        Thread f1ListenerThread = startThread(getF1Listener());
        Thread f2ListenerThread = startThread(getF2Listener());
        Thread f3ListenerThread = startThread(getF3Listener());
        Thread f4ListenerThread = startThread(getF4Listener());
        Thread snapshotSchedule = startThread(getSnapshotSchedule());

        f1ListenerThread.join();
        f2ListenerThread.join();
        f3ListenerThread.join();
        f4ListenerThread.join();
        snapshotSchedule.join();
        serverConnect.shutdown();
    }

    public void start() throws InterruptedException, IOException {
        init();
        getStartupRecoverService().recover();

        String[] numbers = getStartupRecoverService().recoverIds();
        getF2Listener().startLogNumber(numbers[0]);
        getSnapshotSchedule().startSnapNumber(numbers[1]);

        atomixConnection.connect();
        serverConnect.start();
        startListeners();
    }

    private Thread startThread(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.start();
        return t;
    }

    private void printServerInfo(Integer port,  BigInteger biggerKey , BigInteger smallerKey,
                                 Integer leftServer, Integer rightServer) {
        System.out.println("----------------------");
        System.out.println("Server Port -> " + port );
        System.out.println("Server on Left -> " + leftServer);
        System.out.println("Server on Right -> " + rightServer);
        System.out.println("Key range -> [ "+ smallerKey +" , "+ biggerKey +" ]" );
        System.out.println("----------------------");
    }

    public void shutdown() throws InterruptedException {
        serverConnect.shutdown();
    }
}
