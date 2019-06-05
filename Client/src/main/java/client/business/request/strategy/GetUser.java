package client.business.request.strategy;

import client.business.request.observers.GenericObserver;
import io.grpc.GenericRequest;
import io.grpc.GreeterGrpc;

public class GetUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterStub output) {
		GenericRequest request = GenericRequest.newBuilder()
				.setCode(Integer.parseInt(inputParams[1]))
				.build();

		output.getUser(
				request,
				new GenericObserver()
		);
	}
}