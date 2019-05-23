package client.business.request.strategy;

import io.grpc.GetRequest;
import io.grpc.GetResponse;
import io.grpc.GreeterGrpc;

public class GetUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterBlockingStub output) {
		GetRequest request = GetRequest.newBuilder().setId(inputParams[1]).build();
		GetResponse getResponse = output.getUser(request);
		System.out.println(getResponse);
	}
}