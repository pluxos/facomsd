package client.business.request.strategy;

import java.io.IOException;
import java.util.Arrays;

import client.commons.domain.User;
import client.commons.utils.DataCodificator;
import io.grpc.CreateRequest;
import io.grpc.CreateResponse;
import io.grpc.GreeterGrpc;

public class CreateUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterBlockingStub output) {
		User user = new User(inputParams[2], inputParams[3], inputParams[4]);
		try {
			CreateRequest createRequest = CreateRequest.newBuilder()
					.setId(inputParams[1])
					.setData(Arrays.toString(DataCodificator.encode(user)))
					.build();

			CreateResponse createResponse = output.createUser(createRequest);
			System.out.println(createResponse.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}