package client.business.request.strategy;

import io.grpc.*;

public interface RequestStrategy {
	void sendRequest(String[] inputParams, GreeterGrpc.GreeterBlockingStub output);
}