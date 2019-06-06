package client.business.request.strategy;

import client.business.request.observers.GenericObserver;
import client.commons.utils.DataCodificator;
import io.grpc.GenericRequest;
import io.grpc.GreeterGrpc;

public class CreateUser implements RequestStrategy {

    @Override
    public void sendRequest(String[] inputParams, GreeterGrpc.GreeterStub output) {

        String data = DataCodificator.prepareInputs(inputParams);

        GenericRequest createRequest = GenericRequest.newBuilder()
                .setCode(Integer.parseInt(inputParams[1]))
                .setData(data)
                .build();

        output.createUser(
                createRequest,
                new GenericObserver()
        );
    }
}