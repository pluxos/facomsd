package client.business.request.strategy;

import io.grpc.GenericRequest;
import io.grpc.GenericResponse;
import io.grpc.GreeterGrpc;

public class DeleteUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterBlockingStub output) {
		GenericRequest deleteRequest = GenericRequest.newBuilder()
				.setCode(inputParams[1])
				.build();

		GenericResponse deleteResponse = output.deleteUser(deleteRequest);

		System.out.println(deleteResponse.getMessage());
	}
}