package client.business.request.strategy;

import io.grpc.GenericRequest;
import io.grpc.GenericResponse;
import io.grpc.GreeterGrpc;

public class GetUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterBlockingStub output) {
		GenericRequest request = GenericRequest.newBuilder()
				.setCode(inputParams[1])
				.build();

		GenericResponse getResponse = output.getUser(request);
		System.out.println(getResponse.getMessage());
		System.out.println(getResponse.getData());
	}
}