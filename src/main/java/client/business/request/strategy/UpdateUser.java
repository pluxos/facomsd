
package client.business.request.strategy;

import java.io.IOException;
import java.util.Arrays;

import client.commons.domain.User;
import client.commons.utils.DataCodificator;
import io.grpc.*;


public class UpdateUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterBlockingStub output) {
		User user = new User(inputParams[2], inputParams[3], inputParams[4]);
		try {
			UpdateRequest updateRequest = UpdateRequest.newBuilder()
					.setId(inputParams[1])
					.setData(Arrays.toString(DataCodificator.encode(user)))
					.build();

			UpdateResponse updateResponse = output.updateUser(updateRequest);
			System.out.println(updateResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}