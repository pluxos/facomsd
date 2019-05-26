
package client.business.request.strategy;

import client.commons.utils.DataCodificator;
import io.grpc.GenericRequest;
import io.grpc.GenericResponse;
import io.grpc.GreeterGrpc;


public class UpdateUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterBlockingStub output) {
		String data = DataCodificator.prepareInputs(inputParams);

		GenericRequest updateRequest = GenericRequest.newBuilder()
				.setCode(inputParams[1])
				.setData(data)
				.build();

		GenericResponse updateResponse = output.updateUser(updateRequest);
		System.out.println(updateResponse.getMessage());
	}
}