package client.business.request.strategy;

import java.io.IOException;

import client.commons.domain.Method;
import client.commons.domain.User;
import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;
import client.commons.utils.DataCodificator;
import client.connector.GenericRequest;


public class UpdateUser implements RequestStrategy {
	@Override
	public GenericRequest buildRequest(String[] inputParams) throws IOException {
		User user = null;
		GenericRequest request = new GenericRequest();
		try {
			user = new User(inputParams[2], inputParams[3], inputParams[4]);
			request = new GenericRequest();
			request.setMethod(Method.CREATE);
			request.setCode(DataCodificator.stringToBigInteger(inputParams[1]));
			request.setData(DataCodificator.encode(user));
		} catch (NullPointerException e) {
			throw new InvalidCommandException(ErrorMap.INVALID_COMMAND);
		}
		return request;
	}
}