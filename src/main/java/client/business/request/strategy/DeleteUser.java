package client.business.request.strategy;

import io.grpc.DeleteRequest;
import io.grpc.DeleteResponse;
import io.grpc.GreeterGrpc;

public class DeleteUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterBlockingStub output) {
		DeleteRequest deleteRequest = DeleteRequest.newBuilder().setId(inputParams[1]).build();
		DeleteResponse deleteResponse = output.deleteUser(deleteRequest);

		System.out.println(deleteResponse);
	}
}