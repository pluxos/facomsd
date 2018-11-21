package org.kim.grpc.server.threads;

import org.kim.grpc.server.helper.ExecuteHelper;
import org.kim.grpc.server.model.ArrivingGrpc;
import org.kim.grpc.server.model.Operation;
import org.kim.grpc.OperationRequest;

import java.math.BigInteger;
import java.util.logging.Logger;

import static org.kim.grpc.server.helper.DataStorage.getInstance;

public class ThreadProcessGrpc extends Thread {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static ThreadProcessGrpc threadProcessGrpc;

    private ThreadProcessGrpc() { }

    public static ThreadProcessGrpc init(){
        if (threadProcessGrpc == null) {
            threadProcessGrpc = new ThreadProcessGrpc();
            threadProcessGrpc.start();
        }

        return threadProcessGrpc;
    }

    @Override
    public void run() {

        while (true) {
            if (!getInstance().getArrivingGrpc().isEmpty()) {
                String response;

                ArrivingGrpc arrivingGrpc = getInstance().pollArrivingGrpc();

                OperationRequest operationGrpc = arrivingGrpc.getRequestGrpc();

                Operation operation = convertToOp(operationGrpc);

                System.out.println(operation.toString());

                if (operation.getOperation() == 4) response = getInstance().addRegisterHashGrpc(operation.getKey(),arrivingGrpc.getResponseGrpc());

                else response = ExecuteHelper.executeOperation(operation);

                ExecuteHelper.respondClientGrpc(arrivingGrpc.getResponseGrpc(), response);
            }
        }

    }

    private Operation convertToOp(OperationRequest request) {
        Operation operation = new Operation();

        Integer key = Math.toIntExact(request.getKey());
        BigInteger keyBigInteger = BigInteger.valueOf(key);

        operation.setKey(keyBigInteger);
        operation.setOperation((int) request.getOperation());
        operation.setValue(request.getValue());

        return operation;
    }

}
