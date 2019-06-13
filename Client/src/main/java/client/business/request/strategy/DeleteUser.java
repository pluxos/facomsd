package client.business.request.strategy;

import client.business.request.observers.GenericObserver;
import io.grpc.GenericRequest;
import io.grpc.GreeterGrpc;

public class DeleteUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterStub output) {
		GenericRequest deleteRequest = GenericRequest.newBuilder()
				.setCode(Integer.parseInt(inputParams[1]))
				.build();

		output.delete(
				deleteRequest,
				new GenericObserver()
		);
	}
}