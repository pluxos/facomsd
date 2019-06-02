package client.business.request.strategy;

import client.commons.utils.DataCodificator;
import io.grpc.GenericRequest;
import io.grpc.GenericResponse;
import io.grpc.GreeterGrpc;

public class CreateUser implements RequestStrategy {

    @Override
    public void sendRequest(String[] inputParams, GreeterGrpc.GreeterBlockingStub output) {

        String data = DataCodificator.prepareInputs(inputParams);

        GenericRequest createRequest = GenericRequest.newBuilder()
                .setCode(Integer.parseInt(inputParams[1]))
                .setData(data)
                .build();

        GenericResponse createResponse = output.createUser(createRequest);
        System.out.println(createResponse.getMessage());
    }
}