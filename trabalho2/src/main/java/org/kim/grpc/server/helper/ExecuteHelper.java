package org.kim.grpc.server.helper;

import org.kim.grpc.server.model.Operation;
import io.grpc.stub.StreamObserver;
import org.kim.grpc.OperationResponse;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.kim.grpc.server.helper.DataStorage.getInstance;

public class ExecuteHelper extends Thread {


    public static String executeOperation(Operation operation) {
        byte[] response;

        switch (operation.getOperation()) {

            //Create
            case 0:
                response = DataStorage.getInstance().addExecuted(operation.getKey(), operation.getValue()).getBytes();
                DataStorage.getInstance().addLog(operation);
                break;

            //Read
            case 1:
                response = DataStorage.getInstance().getExecuted(operation.getKey()).getBytes();
                break;

            //Update
            case 2:
                response = DataStorage.getInstance().replaceExecuted(operation.getKey(), operation.getValue()).getBytes();
                DataStorage.getInstance().addLog(operation);
                notifyAllClients(operation);
                break;

            //Delete
            case 3:
                notifyAllClients(operation);
                DataStorage.getInstance().removeExecuted(operation.getKey());
                DataStorage.getInstance().addLog(operation);
                response = "Successfully deleted!".getBytes();
                break;

            default:
                response = "Invalid operation!".getBytes();
        }
        String response1 = null;

        try {
            response1 = new String(response, "UTF-8");
        }
        catch (UnsupportedEncodingException e) { e.printStackTrace(); }

        return response1;
    }

    public static void respondClientGrpc(StreamObserver<OperationResponse> responseGrpc, String response) {
        OperationResponse operationResponse = OperationResponse.newBuilder()
                .setResponse(response)
                .build();

        responseGrpc.onNext(operationResponse);
    }

    private static void notifyAllClients(Operation operation) {
        ArrayList<Integer> clientsSocket = DataStorage.getInstance().getRegisterHashSocket(operation.getKey());
        ArrayList<StreamObserver<OperationResponse>> clientsGrpc = DataStorage.getInstance().getRegisterHashGrpc(operation.getKey());

        String resposta = "The key " + operation.getKey() + " has been ";

        if (operation.getOperation() == 2) resposta += "updated!";
        else if (operation.getOperation() == 3) resposta += "excluded!";

        if (clientsGrpc != null) for (StreamObserver<OperationResponse> client: clientsGrpc) respondClientGrpc(client, resposta);
    }

    public static void executeLogOperation(Operation operation) {

        switch (operation.getOperation()) {

            //Create
            case 0:
                getInstance().addExecuted(operation.getKey(), operation.getValue());
                break;

            //Read
            case 1:
                getInstance().getExecuted(operation.getKey());
                break;

            //Update
            case 2:
                getInstance().replaceExecuted(operation.getKey(), operation.getValue());
                break;

            //Delete
            case 3:
                getInstance().removeExecuted(operation.getKey());
                break;

            default:
                break;
        }
    }
}
