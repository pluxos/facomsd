
package client.business.request.strategy;

import client.business.request.observers.GenericObserver;
import client.commons.utils.DataCodificator;
import io.grpc.GenericRequest;
import io.grpc.GreeterGrpc;


public class UpdateUser implements RequestStrategy {

	@Override
	public void sendRequest(String[] inputParams, GreeterGrpc.GreeterStub output) {
		String data = DataCodificator.prepareInputs(inputParams);

		GenericRequest updateRequest = GenericRequest.newBuilder()
				.setCode(Integer.parseInt(inputParams[1]))
				.setData(data)
				.build();

		output.update(
				updateRequest,
				new GenericObserver()
		);
	}
}