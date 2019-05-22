package client.business.request.strategy;

import java.io.IOException;
import client.connector.GenericRequest;
import io.grpc.examples.helloworld.*;

public interface RequestStrategy {

	GenericRequest buildRequest(String[] inputParams) throws IOException;
	
	default void makeRequest(GenericRequest request, GreeterGrpc.GreeterBlockingStub output) {

		switch (request.getMethod()){
			case CREATE:
				CreateResponse createResponse = output.createUser(CreateRequest.newBuilder().build());
				System.out.println(createResponse.getMessage());
				break;
			case UPDATE:
				UpdateResponse updateResponse = output.updateUser(UpdateRequest.newBuilder().build());
				System.out.println(updateResponse.getMessage());
				break;
			case GET:
				GetResponse getResponse = output.getUser(GetRequest.newBuilder().build());
				System.out.println(getResponse.getEmail());
				break;
			case DELETE:
				DeleteResponse deleteResponse = output.deleteUser(DeleteRequest.newBuilder().build());
				System.out.println(deleteResponse.getMessage());
				break;
			default:
				System.out.println("Comando inválido!");
		}
	}
}