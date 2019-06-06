package client.business.request.strategy;

import io.grpc.GreeterGrpc;

public interface RequestStrategy {
	void sendRequest(String[] inputParams, GreeterGrpc.GreeterStub output);
}