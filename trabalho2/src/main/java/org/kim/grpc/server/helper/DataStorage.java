package org.kim.grpc.server.helper;

import org.kim.grpc.server.model.ArrivingGrpc;
import org.kim.grpc.server.model.Operation;
import io.grpc.stub.StreamObserver;
import org.kim.grpc.OperationResponse;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class DataStorage {

    private ConcurrentHashMap<BigInteger, String> executed;
    private ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>> registerHashGrpc;
    private ConcurrentHashMap<BigInteger, ArrayList<Integer>> registerHashSocket;
    private BlockingQueue<Operation> toLog;
    private BlockingQueue<ArrivingGrpc> arrivingGrpc;

    private static DataStorage dataStorage;

    private DataStorage() {
        executed = new ConcurrentHashMap<BigInteger, String>();
        toLog = new LinkedBlockingDeque<Operation>();
        arrivingGrpc = new LinkedBlockingDeque<ArrivingGrpc>();
        registerHashGrpc = new ConcurrentHashMap<BigInteger, ArrayList<StreamObserver<OperationResponse>>>();
        registerHashSocket = new ConcurrentHashMap<BigInteger, ArrayList<Integer>>();

    }

    public synchronized static DataStorage getInstance() {
        if (dataStorage == null) dataStorage = new DataStorage();

        return dataStorage;
    }

    public synchronized String getExecuted(BigInteger key) {
        if (executed.containsKey(key)) return executed.get(key);

        return "No key!";
    }

    public synchronized String addExecuted(BigInteger key, String value) {
        if (!executed.containsKey(key)) {
            executed.put(key, value);
            return "Successfully inserted!";
        }

        return "Key already exists!";
    }

    public synchronized String replaceExecuted(BigInteger key, String value) {
        if (executed.containsKey(key)) {
            executed.replace(key, value);
            return "Successfully updated!";
        }

        return "No key!";

    }

    public synchronized String addRegisterHashSocket(BigInteger key, Integer port) {
        ArrayList<Integer> clientList;

        if (!executed.containsKey(key)) return "Key not yet created";

        if (!registerHashSocket.containsKey(key)) {
            clientList = new ArrayList<Integer>();
            clientList.add(port);
            registerHashSocket.put(key, clientList);
        } else {
            clientList = registerHashSocket.get(key);
            clientList.add(port);
            registerHashSocket.put(key, clientList);
        }

        return "Successfully registered!";
    }

    public synchronized ArrayList<Integer> getRegisterHashSocket(BigInteger key) {
        return registerHashSocket.get(key);
    }

    public synchronized String addRegisterHashGrpc(BigInteger key, StreamObserver<OperationResponse> listener) {
        ArrayList<StreamObserver<OperationResponse>> clientList;

        if (!executed.containsKey(key)) return "Key not yet existent";

        if (!registerHashGrpc.containsKey(key)) registerHashGrpc.put(key, new ArrayList<StreamObserver<OperationResponse>>(Collections.singleton(listener)));
        else {
            clientList = registerHashGrpc.get(key);
            clientList.add(listener);
            registerHashGrpc.put(key, clientList);
        }
        return "Successfully registered!";
    }

    public synchronized ArrayList<StreamObserver<OperationResponse>> getRegisterHashGrpc(BigInteger key) {
        return registerHashGrpc.get(key);
    }

    public synchronized void addLog(Operation operation) {
        toLog.add(operation);
    }

    public synchronized Operation pollLog() {
        return toLog.poll();
    }

    public synchronized void addArrivingGrpc(ArrivingGrpc o) {
        arrivingGrpc.add(o);
    }

    public synchronized ArrivingGrpc pollArrivingGrpc() {
        return arrivingGrpc.poll();
    }

    public Queue<ArrivingGrpc> getArrivingGrpc() {
        return arrivingGrpc;
    }

    public synchronized void removeExecuted(BigInteger key) {
        executed.remove(key);
        registerHashSocket.remove(key);
        registerHashGrpc.remove(key);
    }

    public Queue<Operation> getLog() {
        return toLog;
    }
}
